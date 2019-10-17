import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { FormModel } from '../../../../interface/form.model';
import { FormControlService } from '../../../../service/form-control.service';

declare var $: any;

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss'],
  providers: [FormControlService]
})
export class DynamicFormComponent implements OnInit {

  @Input() formFields: FormModel<any>[];
  form: FormGroup;
  payLoad = '';

  constructor(private formControlService: FormControlService) { }

  ngOnInit() {
    this.form = this.formControlService.toFormGroup(this.formFields);
  }
  
  submitForm(){
    console.log(this.formFields);
  }

  selectDropdown(selectedOption, model, index){
    this.formFields[index].key = selectedOption.value;
    this.formFields[index].value = selectedOption.value;
  }

  ngAfterViewInit(){
    $("input, textarea, .select-dropdown").focus(function() {
      $(this).closest(".input-holder").parent().find("> label").css({"color": "#4285F4"})
      
    })
    $("input, textarea, .select-dropdown").blur(function(){
      $(this).closest(".input-holder").parent().find("> label").css({"color": "#333"})
    })
  }
}
