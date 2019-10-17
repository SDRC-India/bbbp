import { Component, OnInit } from '@angular/core';
import { StaticPageService } from '../../services/static-page.service';
import { document } from 'angular-bootstrap-md/utils/facade/browser';
import { Constants } from '../../../../constants';
import { log } from 'util';
declare var $: any;
@Component({
  selector: 'app-audio-gallery',
  templateUrl: './audio-gallery.component.html',
  styleUrls: ['./audio-gallery.component.scss']
})
export class AudioGalleryComponent implements OnInit {

  p: number = 1;
  searchTexts: any;
  staticService: StaticPageService;
  audioData: any;
  selectionFields: any;
  filteredGalleryDetails: any[] = [];
  constructor(private staticServiceProvider: StaticPageService) {
    this.staticService = staticServiceProvider;
  }

  ngOnInit() {
    this.staticService.getDataEntryTimeperiodSelection().subscribe(data => {
      this.selectionFields = data;
    });
    this.staticService.getData("Audio Gallery").subscribe(data => {
      this.staticService.reinitializeData(data);
      this.filteredGalleryDetails = this.staticService.contentList;
      // this.audioData = data;
      // let i = 0;
      // this.audioData.viewContent[this.staticService.selectedLang][0].data.forEach(element => {
      //   console.log(element);
      //   let audio = document.getElementById("myAudio" +i); 
      //   let audioSource = document.getElementById("source" +i);
      //   audioSource.src = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+element.fileName;
      //   audio.controls =  true;
      //   audio.load();
      // });


    })
  }

  selectDropdown(selectedOption, model, index) {
    this.selectionFields[index].errorFound = false;
    this.selectionFields[index].errorMessage = "";
    this.selectionFields[index].value = selectedOption.value;
    this.selectionFields[index].key = selectedOption.key;
    this.filteredGalleryDetails = [];

    this.staticService.contentList.forEach(gd => {

      if ((gd.year && gd.quarter && (gd.year=== model[0].value) && gd.quarter === model[1].value) ||
        (!model[1].value && gd.year === selectedOption.value)
        || (!model[0].value && gd.quarter === selectedOption.value)) {
        this.filteredGalleryDetails.push(gd);
      }

    })
  }

  resetVal(modelVal){
   modelVal.forEach(element => {
     if(element.label == "Select Year"){
       element.value = undefined;
     }
     if(element.label == "Select Quarter"){
      element.value = undefined;
    }
   });
   this.staticService.getData("Audio Gallery").subscribe(data => {
    this.staticService.reinitializeData(data);
    this.filteredGalleryDetails = this.staticService.contentList;
  })
  }

  playAudio(fileName, i) {
    let audio = document.getElementById("myAudio" + i);
    let audioSource = document.getElementById("source" + i);
    audio.controls = true;
    audioSource.src = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName=' + fileName;

    // audio.src = fileName;
    audio.load();
    audio.preload = "auto";
    //   audio.onloadeddata = function() {
    //     alert("Browser has loaded the current frame");
    // };
    audio.play();
    document.getElementById('play' + i).style.display = 'none';
    // let w = $(window).width();
    // if(w < 769 && audio.muted == false) {
    //   document.getElementById('audio-link').style.marginLeft = '-16px';
    //   document.getElementsByTagName('audio').style.maxWidth = '104%';
    // }
    document.addEventListener('play', function (e) {
      var allAudios = document.getElementsByTagName('audio');
      for (var i = 0; i < allAudios.length; i++) {
        if (allAudios[i] != e.target) {
          allAudios[i].pause();
        }
      }
    }, true);

  }

}
