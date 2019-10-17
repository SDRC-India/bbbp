import { Component, ElementRef, Input, OnChanges } from '@angular/core';
import * as d3 from 'd3v4';

import { DashboardService } from '../services/dashboard.service';

declare var jquery:any;
declare var $ :any;


@Component({
  selector: 'app-horizontal-bar-chart',
  templateUrl: './horizontal-bar-chart.component.html',
  styleUrls: ['./horizontal-bar-chart.component.scss']
})
export class HorizontalBarChartComponent implements OnChanges {
  dashboardService: DashboardService;

  @Input()
  data: any;

  constructor(private dashboardServiceProvider: DashboardService, private hostRef: ElementRef) { 
    this.dashboardService = dashboardServiceProvider;
  }

  ngOnChanges(changes){
    this.mergeAllStatesWithMapData();
    if(changes.data.currentValue && changes.data.currentValue.length && changes.data.currentValue != changes.data.previousValue && this.dashboardService.submittedIndicator.unitName == "NUMBER"){
      this.createChart(this.dashboardService.allStates);
    }
    else{
      this.createBlankChart(this.dashboardService.allStates);
    }
  }

  mergeAllStatesWithMapData(){
    if(this.dashboardService.mapData){
      for (let i = 0; i < this.dashboardService.mapData.length; i++) {
        let  mapData = this.dashboardService.mapData[i];
        for (let j = 0; j < this.dashboardService.allStates.length; j++) {
          let state = this.dashboardService.allStates[j];
          if(mapData.areaId == state.areaId){
            this.dashboardService.allStates[j] = mapData;
          }
        }
        
      }
      
      // this.dashboardService.allStates.sort((a, b) =>{
      //   return a.areaName > b.areaName
      // })
    }
    else{
      this.createBlankChart(this.dashboardService.allStates);
    }
  }


  createBlankChart(data){
    d3.select("svg").html("")
    var svg = d3.select("svg").attr("width", $(this.hostRef.nativeElement).parent().width()).attr("height", function(){
        return 700
  
    }),
    margin = {top: 0, right: 20, bottom: 100, left: 80}
    if($(window).width() > 767){
      margin = {top: 0, right: 60, bottom: 100, left: 150}
    }
    var width = +svg.attr("width") - margin.left - margin.right
    var height = +svg.attr("height") - margin.top - margin.bottom;
  
    var tooltip = d3.select("body").append("div").attr("class", "toolTip horizontal-bar-tip");
      
    var x = d3.scaleLinear().range([0, width]);
    var y = d3.scaleBand().range([height, 0]);

    var g = svg.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    x.domain([0, d3.max(data, function(d) { return 0; })]);
    y.domain(data.map(function(d) { return d.areaName; })).padding(0.3);

    g.append("g")
        .attr("class", "x axis")
       	.attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x).ticks(5).tickFormat(d3.format(".2f")).tickSizeInner([-height]));
        // .text("Value (in numbers)");
// 
    g.append("g")
        .attr("class", "y axis")
        .call(d3.axisLeft(y));
    
    g.append("text")
      .style("font-size", "30px")
      .style("font-weight", "bold").style("letter-spacing", "2px")
      .style("fill", "#a53737").style("transform", "translate("+ (width/2 - 135) + "px, "+ height/2 +"px)")
      .style("left", "50px")
      .text("No Data Available")

  }

  createChart(data){
    d3.select("svg").html("")
    var svg = d3.select("svg").attr("width", $(this.hostRef.nativeElement).parent().width()).attr("height", function(){
      if(data.length * 40 > 600){
        return 700
      }
      else{
        return 350
      }
    }),
    margin = {top: 0, right: 20, bottom: 100, left: 80}
    if($(window).width() > 767){
      margin = {top: 0, right: 60, bottom: 100, left: 150}
    }
    svg.append('text')
    .style("fill", "#cecece")
    .style("font-weight", "bold")
    .style("transform", "translate(35%, 70%) rotate(-30deg)")
    .style("font-size", "53px")
    .style("letter-spacing", "9px")
    .text('Sample Data')
    var width = +svg.attr("width") - margin.left - margin.right
    var height = +svg.attr("height") - margin.top - margin.bottom;
  
    var tooltip = d3.select("body").append("div").attr("class", "toolTip horizontal-bar-tip");
      
    var x = d3.scaleLinear().range([0, width]);
    var y = d3.scaleBand().range([height, 0]);

    var g = svg.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    x.domain([0, d3.max(data, function(d) { return d.value; })]);
    y.domain(data.map(function(d) { return d.areaName; })).padding(0.3);

    g.append("g")
        .attr("class", "x axis")
       	.attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x).ticks(5))
        .append("text").attr("x",
       width-30).attr("y",
       "35").attr("dx", ".1em")																			
   .style("fill", "#333")
   .style("font-weight", "bold")
   .style("font-size", "13px")
   .text("Value (in numbers)")
// 
    g.append("g")
        .attr("class", "y axis")
        .call(d3.axisLeft(y));

    var bars = g.selectAll(".bar")
        .data(data)
      .enter().append("g")
      bars.append("rect")
        .attr("class", "bar horizontal-chart-bars")
        .attr("x", 1)
        .attr("height", y.bandwidth()
        // function(){
        //   if(y.bandwidth() < 40){
        //     return y.bandwidth()
        //   }
        //   else{
        //     return 40;
        //   }
        //   }
        )
        .attr("y", function(d) { return y(d.areaName); })
        .attr("width", function(d) { 
          if(d.value)
            return x(d.value);
          else
            return width;
         })

        .on("mousemove", function(d){
            tooltip
              .style("left", d3.event.pageX - 50 + "px")
              .style("top", d3.event.pageY - 70 + "px")
              .style("display", "inline-block")
              .html('AreaName: '+(d.areaName) + "<br>" + "Value: " + (d.value ? d.value: "Not Available"));
        })
        .on("click", (d) => {
        if(d.value)
        this.getTrendChart(d)})
        .on("mouseout", function(d){ tooltip.style("display", "none");})
        .style("fill", (d) => {
          if(d.value){
            return '#0275d8'
          }
          else{
            return '#DDD'
          }
        })
        .style("cursor", (d) => {
          if(d.value){
            return 'pointer'
          }
          else{
            return 'default'
          }
        });
        bars.append("text")
          .attr("class", "label")
          //y position of the label is halfway down the bar
          .attr("y", function (d) {
              return y(d.areaName) + y.bandwidth() / 2 + 4;
          })
          //x position is 3 pixels to the right of the bar
          .attr("x", function (d) {
            if(d.value)
              return x(d.value) + 3;
            else
            return x(d.value)/2;
          })
          .attr("transform", (d) => {
            if(d.value == null){
              return "translate(30, 0)"
            }
          })
          .style("fill", (d) => {
            if(d.value == null){
              return "#999"
            }
          })
          .style("font-size", (d) => {
            if(d.value == null){
              return "10px"
            }
          }).style("letter-spacing", (d) => {
            if(d.value == null){
              return "2px"
            }
          })
          
          .text(function (d) {
            if(d.value || d.value == 0)
              return d.value;
            else{
              return "Not Available"
            }
        });
    //     bars.append("text").attr("class", "not-avail")
    //     .attr("y", function (d) {
    //       return y(d.areaName) + y.bandwidth() / 2 + 4;
    //   })
    //   .attr("x", function (d) {
    //     return x(d.value)/2;
    // })
  }

  getTrendChart(d){
    this.dashboardService.getTrendChartData(d.areaNid).subscribe(res => {
      this.dashboardService.trendChartData = res;
      setTimeout(()=>{
        this.dashboardService.dragElement(document.getElementById("trend-chart-container"))
      }, 1000)
    })
  }

}
