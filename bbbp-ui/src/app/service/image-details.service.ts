import { Injectable } from '@angular/core';

@Injectable()
export class ImageDetailsService {

  visibleImages = [];
  getImages() {
   return this.visibleImages = IMAGES.slice(0);
  }


  getImage(id: number) {
    return IMAGES.slice(0).find(Image => Image.id == id)
  }
  constructor() { }

}

const IMAGES = [
  {
    "id": 1,
    "category": "bbbp-gallery-1",
    "caption": "view from bbbp gallery1",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 2,
    "category": "bbbp-gallery-2",
    "caption": "view from bbbp gallery2",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 3,
    "category": "bbbp-gallery-3",
    "caption": "view from bbbp gallery3",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 4,
    "category": "bbbp-gallery-4",
    "caption": "view from bbbp gallery4",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 5,
    "category": "bbbp-gallery-5",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 6,
    "category": "bbbp-gallery-6",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 7,
    "category": "bbbp-gallery-7",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 8,
    "category": "bbbp-gallery-8",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 9,
    "category": "bbbp-gallery-9",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 10,
    "category": "bbbp-gallery-10",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 11,
    "category": "bbbp-gallery-11",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 12,
    "category": "bbbp-gallery-12",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 13,
    "category": "bbbp-gallery-13",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 14,
    "category": "bbbp-gallery-14",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  },
  {
    "id": 15,
    "category": "bbbp-gallery-15",
    "caption": "view from bbbp gallery5",
    "url": "./assets/images/gallery/Inspiring-Beti-Bachao-Beti-Padhao-Pictures-20-326x235.jpg"
  }
]