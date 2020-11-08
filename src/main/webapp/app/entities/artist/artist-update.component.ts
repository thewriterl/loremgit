import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IArtist, Artist } from 'app/shared/model/artist.model';
import { ArtistService } from './artist.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';
import { IExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from 'app/entities/external-url/external-url.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album/album.service';
import { ITrack } from 'app/shared/model/track.model';
import { TrackService } from 'app/entities/track/track.service';

type SelectableEntity = IImage | IExternalURL | IAlbum | ITrack;

@Component({
  selector: 'jhi-artist-update',
  templateUrl: './artist-update.component.html',
})
export class ArtistUpdateComponent implements OnInit {
  isSaving = false;
  images: IImage[] = [];
  externalurls: IExternalURL[] = [];
  albums: IAlbum[] = [];
  tracks: ITrack[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    popularity: [],
    idSpotify: [],
    href: [],
    followers: [],
    image: [],
    externalURL: [],
    album: [],
    track: [],
  });

  constructor(
    protected artistService: ArtistService,
    protected imageService: ImageService,
    protected externalURLService: ExternalURLService,
    protected albumService: AlbumService,
    protected trackService: TrackService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artist }) => {
      this.updateForm(artist);

      this.imageService
        .query({ filter: 'artist-is-null' })
        .pipe(
          map((res: HttpResponse<IImage[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IImage[]) => {
          if (!artist.image || !artist.image.id) {
            this.images = resBody;
          } else {
            this.imageService
              .find(artist.image.id)
              .pipe(
                map((subRes: HttpResponse<IImage>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IImage[]) => (this.images = concatRes));
          }
        });

      this.externalURLService
        .query({ filter: 'artist-is-null' })
        .pipe(
          map((res: HttpResponse<IExternalURL[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExternalURL[]) => {
          if (!artist.externalURL || !artist.externalURL.id) {
            this.externalurls = resBody;
          } else {
            this.externalURLService
              .find(artist.externalURL.id)
              .pipe(
                map((subRes: HttpResponse<IExternalURL>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExternalURL[]) => (this.externalurls = concatRes));
          }
        });

      this.albumService.query().subscribe((res: HttpResponse<IAlbum[]>) => (this.albums = res.body || []));

      this.trackService.query().subscribe((res: HttpResponse<ITrack[]>) => (this.tracks = res.body || []));
    });
  }

  updateForm(artist: IArtist): void {
    this.editForm.patchValue({
      id: artist.id,
      name: artist.name,
      popularity: artist.popularity,
      idSpotify: artist.idSpotify,
      href: artist.href,
      followers: artist.followers,
      image: artist.image,
      externalURL: artist.externalURL,
      album: artist.album,
      track: artist.track,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const artist = this.createFromForm();
    if (artist.id !== undefined) {
      this.subscribeToSaveResponse(this.artistService.update(artist));
    } else {
      this.subscribeToSaveResponse(this.artistService.create(artist));
    }
  }

  private createFromForm(): IArtist {
    return {
      ...new Artist(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      popularity: this.editForm.get(['popularity'])!.value,
      idSpotify: this.editForm.get(['idSpotify'])!.value,
      href: this.editForm.get(['href'])!.value,
      followers: this.editForm.get(['followers'])!.value,
      image: this.editForm.get(['image'])!.value,
      externalURL: this.editForm.get(['externalURL'])!.value,
      album: this.editForm.get(['album'])!.value,
      track: this.editForm.get(['track'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArtist>>): void {
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
