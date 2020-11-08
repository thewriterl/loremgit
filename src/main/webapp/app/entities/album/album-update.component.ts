import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAlbum, Album } from 'app/shared/model/album.model';
import { AlbumService } from './album.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';
import { IExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from 'app/entities/external-url/external-url.service';

type SelectableEntity = IImage | IExternalURL;

@Component({
  selector: 'jhi-album-update',
  templateUrl: './album-update.component.html',
})
export class AlbumUpdateComponent implements OnInit {
  isSaving = false;
  images: IImage[] = [];
  externalurls: IExternalURL[] = [];
  releaseDateDp: any;

  editForm = this.fb.group({
    id: [],
    albumType: [],
    href: [],
    idSpotify: [],
    label: [],
    name: [],
    popularity: [],
    releaseDate: [],
    uri: [],
    image: [],
    externalURL: [],
  });

  constructor(
    protected albumService: AlbumService,
    protected imageService: ImageService,
    protected externalURLService: ExternalURLService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ album }) => {
      this.updateForm(album);

      this.imageService
        .query({ filter: 'album-is-null' })
        .pipe(
          map((res: HttpResponse<IImage[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IImage[]) => {
          if (!album.image || !album.image.id) {
            this.images = resBody;
          } else {
            this.imageService
              .find(album.image.id)
              .pipe(
                map((subRes: HttpResponse<IImage>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IImage[]) => (this.images = concatRes));
          }
        });

      this.externalURLService
        .query({ filter: 'album-is-null' })
        .pipe(
          map((res: HttpResponse<IExternalURL[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExternalURL[]) => {
          if (!album.externalURL || !album.externalURL.id) {
            this.externalurls = resBody;
          } else {
            this.externalURLService
              .find(album.externalURL.id)
              .pipe(
                map((subRes: HttpResponse<IExternalURL>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExternalURL[]) => (this.externalurls = concatRes));
          }
        });
    });
  }

  updateForm(album: IAlbum): void {
    this.editForm.patchValue({
      id: album.id,
      albumType: album.albumType,
      href: album.href,
      idSpotify: album.idSpotify,
      label: album.label,
      name: album.name,
      popularity: album.popularity,
      releaseDate: album.releaseDate,
      uri: album.uri,
      image: album.image,
      externalURL: album.externalURL,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const album = this.createFromForm();
    if (album.id !== undefined) {
      this.subscribeToSaveResponse(this.albumService.update(album));
    } else {
      this.subscribeToSaveResponse(this.albumService.create(album));
    }
  }

  private createFromForm(): IAlbum {
    return {
      ...new Album(),
      id: this.editForm.get(['id'])!.value,
      albumType: this.editForm.get(['albumType'])!.value,
      href: this.editForm.get(['href'])!.value,
      idSpotify: this.editForm.get(['idSpotify'])!.value,
      label: this.editForm.get(['label'])!.value,
      name: this.editForm.get(['name'])!.value,
      popularity: this.editForm.get(['popularity'])!.value,
      releaseDate: this.editForm.get(['releaseDate'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      image: this.editForm.get(['image'])!.value,
      externalURL: this.editForm.get(['externalURL'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlbum>>): void {
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
