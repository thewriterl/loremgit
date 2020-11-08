import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGenre, Genre } from 'app/shared/model/genre.model';
import { GenreService } from './genre.service';
import { IArtist } from 'app/shared/model/artist.model';
import { ArtistService } from 'app/entities/artist/artist.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album/album.service';

type SelectableEntity = IArtist | IAlbum;

@Component({
  selector: 'jhi-genre-update',
  templateUrl: './genre-update.component.html',
})
export class GenreUpdateComponent implements OnInit {
  isSaving = false;
  artists: IArtist[] = [];
  albums: IAlbum[] = [];

  editForm = this.fb.group({
    id: [],
    genre: [],
    artist: [],
    album: [],
  });

  constructor(
    protected genreService: GenreService,
    protected artistService: ArtistService,
    protected albumService: AlbumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ genre }) => {
      this.updateForm(genre);

      this.artistService.query().subscribe((res: HttpResponse<IArtist[]>) => (this.artists = res.body || []));

      this.albumService.query().subscribe((res: HttpResponse<IAlbum[]>) => (this.albums = res.body || []));
    });
  }

  updateForm(genre: IGenre): void {
    this.editForm.patchValue({
      id: genre.id,
      genre: genre.genre,
      artist: genre.artist,
      album: genre.album,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const genre = this.createFromForm();
    if (genre.id !== undefined) {
      this.subscribeToSaveResponse(this.genreService.update(genre));
    } else {
      this.subscribeToSaveResponse(this.genreService.create(genre));
    }
  }

  private createFromForm(): IGenre {
    return {
      ...new Genre(),
      id: this.editForm.get(['id'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      artist: this.editForm.get(['artist'])!.value,
      album: this.editForm.get(['album'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenre>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
