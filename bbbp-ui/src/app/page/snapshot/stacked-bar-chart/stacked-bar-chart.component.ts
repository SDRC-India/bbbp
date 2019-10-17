import { Component, OnInit, ElementRef, Input } from '@angular/core';
import * as d3 from 'd3';
declare var $: any;
@Component({
  selector: 'app-stacked-bar-chart',
  templateUrl: './stacked-bar-chart.component.html',
  styleUrls: ['./stacked-bar-chart.component.scss']
})
export class StackedBarChartComponent implements OnInit {

  clickedKeys = [];
  @Input('data') private dataToStack
  totalData = [];

  @Input() private keys;
  reorderColors = [];

  x: any;
  y: any;
  stackedSeries;
  margin;
  width;
  height;
  constructor(private hostRef: ElementRef) { }

  ngOnInit() {
    if (this.dataToStack) {
      console.log("keys -----" + this.keys);
      console.log("data -----" + this.dataToStack);
      this.setupChart()
    }
  }

  setupChart() {

    
    this.margin = {
      top: 20,
      bottom: 30,
      left: 50,
      right: 20,
    };
    let w = $(this.hostRef.nativeElement).parent().width();
    let h = $(this.hostRef.nativeElement).parent().height();


    this.width = w - this.margin.left - this.margin.right;
    this.height = h - this.margin.top - this.margin.bottom - 35;

    let colors = ['#1f4a7c', '#a5b7f3', '#e0e5f7'];
    let colorsOrder = ['#1f4a7c', '#a5b7f3', '#e0e5f7'];

    this.createStack();


    this.x = d3.scaleBand()
      .domain(this.dataToStack.map(function (d) {
        //let date = new Date(d.date);
        return d.axis;
      }))
      .rangeRound([0, this.width])
      .padding(0.05);

    this.y = d3.scaleLinear()
      .domain([0, d3.max(this.stackedSeries, function (d) {
        return d3.max(d, (d) => {
          return d[1];
        })
      })])
      .range([this.height, 0]);

    let svg = d3.select('.chart').append('svg')
      .attr('class', 'chart')
      .attr('width', w)
      .attr('height', h);

    //background image
    var defs = svg.append('svg:defs');

    defs.append("svg:pattern")
        .attr("id", "grump_avatar")
        .attr("width", 250)
        .attr("height", 250)
        .attr("patternUnits", "userSpaceOnUse")
        .append("svg:image")
        .attr("xlink:href", 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAASFBMVEX///+Wlpb7+/ufn5/19fWmpqaamprv7+/Z2dng4ODBwcG3t7fn5+fQ0NCurq7IyMjNzc2ysrLk5OTd3d3x8fGjo6OqqqrExMTDfR+aAAADC0lEQVR4nO3d0Y6iQBSE4SOgIIgIAr7/my7TDa7NuhO96BAq/yTmJKal+G7EEaHMfv6GzI2qc+N2c6Or3EgGN+zkx7F3o7y7UTRu5K1fmvo1aeLG9eJGU7hxL93ox3mNvUuuX5Oz35Jrn3x5n5y78fDJP5vwz7fzHgXAbH7tIdyjcxBz9TGnYOnoY0ofU3hgfgzWrJI98Pw2Of08+RgkA9w7sJAHPmzQBk7JmTrQAAIEuDUwlwderdIGTsmdOtAAagDD/8oA7guYqAMtXX9xoAac1mi/ycxrAAIECDAqMFUHTsnyQAMIECDA2MD2eW5RFHi92KAONIAAAQKMDWzkgcXycxxVoE8GCBAgwKjAuzrwXNpRGzgl9+pAAwgQ4NbAXh2YHa3UBqbumK8NNIAAAQKMDTwtV5+pAg/u+0RtoAEECBBgbODwc1GJMnBKbtWBBhAgQICxgZU8sLPweTmgTwYIECDAqMBaHnhbjhaqwMZdJ6sNNIAAAW4N7NSBl2q5AZ8qsJ0e6kADCBDg1sBMHZgMyx1iVYFTsjzQAAIECDA2MF1uJK4KnJbKAw0gQIAAYwNH67WBQ2+jOtAAAgQIMDawlAfel/P4qkCfDBAgQIBRgYU8sFl3dqkBS1t1dgkCDSBAgFsDL+rAl84uUeD47OzSBRpAgAC3BibqwL+dXarAg7suSBtoAAECBBgb+L/OLhng9ApxoE8GCBAgwKjAVh14zVedXXJAnwwQIECAUYGNPPDZ2aUK9MkAAQIEGBV4lwd+1dm1R2D1TWfXToEGECDArYEfdnbtF5iNH3V27Rg4fNTZtW+gAQQIEGBs4EkdeLBfO7skgAYQIECAsYGDOjBN3nd26QB9MkCAAAFGBf7T2SUHXHd2CQINIECAAGMDb/LAejlaqAJvNnd2CQMNIECAWwM7eeBXnV17BE7J8kADCBDg1sBMHZgM9tAGTsnyQAOoAXz4mPlc6bzzqY9Zbjro92juTlpKMeZNDEFM/fxE+LpH81bH543EXXL4W5c0WNrmHyQnAbB+k/wH1Ayx7Fv/L9QAAAAASUVORK5CYII=')
        .attr("width", 250)
        .attr("height", 250)
        .attr("x", 0)
        .attr("y", 0);

    let chart = svg.append('g')
      .classed('graph', true)
      .attr('transform', 'translate(' + this.margin.left + ',' + this.margin.top + ')');

    const layersBarArea = chart.append('g')
      .attr('class', 'layers');

    this.drawChart(layersBarArea, colors);

    chart.append('g')
      .classed('x axis', true)
      .attr("transform", "translate(0," + this.height + ")")
      .call(d3.axisBottom(this.x));

    chart.append('g')
      .classed('y axis', true)
      .call(d3.axisLeft(this.y)
        .ticks(10));

    //------------------------- Legend ------------------------//

    let legend = d3.select(".legend").append('ul');

    let legendItems = legend.selectAll('li')
      .data(this.keys)
      .enter()
      .append('li')
      .attr('data-key', (d, i) => {
        return d;
      })
      .attr('data-index', (d, i) => {
        return i;
      })
      .attr('data-color', (d, i) => {
        return colors[i];
      })

    legendItems.append('span')
      .attr('class', 'rect')
      .style('background-color', (d, i) => {
        return colors[i];
      });

    legendItems.append('span')
      .attr('class', 'label')
      .html((d) => {
        return d
      });


  }

  getKeys() {
    return this.keys;
  }

  createStack() {
    let stack = d3.stack()
      .keys(this.getKeys());
    this.stackedSeries = stack(this.dataToStack);
  }

  drawChart(layersBarArea, colors) {

    layersBarArea.selectAll('g.layer').remove();

    let layersBar = layersBarArea.selectAll('.layer').data(this.stackedSeries)
      .enter()
      .append('g')
      .attr('class', 'layer')
      .attr('attr-key', (d, i) => {
        return d.key;
      })
      .style('fill', (d, i) => {
        if(i == 0){
          return colors[i];
        }
        else{
          // colors[i];
          return "url(#grump_avatar)"
        }
      })

    

    layersBar.selectAll('rect')
      .data((d) => {
        return d
      })
      .enter()
      .append('rect')
      .attr('height', 0)
      .attr("y", this.height)
      .attr('x', (d, i) => {
        return this.x(d.data.axis)
      })
      .on("mouseover", function (d, i) {
        showPopover.call(this, d, i);
      })
      .on("mouseout", function (d){
        removePopovers();
      })
      .attr('width', this.x.bandwidth())
      .transition()
      .duration(1000)
      .attr('height', (d, i) => {
        return this.y(d[0]) - this.y(d[1]);
      })
      .attr('y', (d) => {
        return this.y(d[1]);
      })
      layersBar.selectAll('val-text')
      .data((d) => {
        return d
      })
      .enter()
      .append('text')
      .attr("class", "val-text")
      .attr('x', (d, i) => {
        return this.x(d.data.axis) + this.x.bandwidth()/2
      }) 
                  .attr('y', (d) => {
                    return this.y(d[1])+(this.y(d[0]) - this.y(d[1]))/2+5;
                  })
                  .style("fill", "#FFF")
                  .style("text-anchor", "middle")
                  .style("font-size", "13px")
                  .text(function(d) {
                return Math.round(d[1] - d[0]);
              });
      

      function showPopover(d, i) {
        $(this).popover(
            {
              title : '',
              placement : 'top',
              container : 'body',
              trigger : 'manual',
              html : true,
              animation: false,
              content : function() {
                if(d.data.axis != "")
                return "<div style='color: #257ab6;'>" + d.data.axis + "</div>" +  $(this).parent().attr("attr-key") + " : "
                    + (d[1]-d[0]);
                else
                  return "<div style='color: #257ab6;'> Average: " + d.value + "%</div>" ;
              }
            });
        $(this).popover('show');
      }

      function removePopovers() {
        $('.popover').each(function() {
          $(this).remove();
        });
      }

    // var e0Arr = [];
    // for (var i = 0; i < this.dataToStack.length; i++) {
    //   e0Arr.push(this.dataToStack[i][0].value);
    //   layersBar.selectAll("value-text" + i).data(this.dataToStack[i]).enter()
    //       .append("text").attr(
    //           "x",
    //           function(d) {
    //             return i == 0 ? this.x(d.axis)
    //                 + this.x.bandwidth() / 2 - 9
    //                 : this.x(d.key) + this.x.bandwidth()
    //                     / 2 + 12;
    //           }) 
    //       .attr("y", function(d) {
    //         return this.y(d.value) - 3;
    //       }).style("text-anchor", "start").style("fill",
    //           "#000000").text(function(d) {
    //         return Math.round(d.value);
    //       });
    //   } 

      
  }

   

//   checkChart(key, index, color){
//     if(this.clickedKeys.includes(key)){
//         this.keys.push(key);
//         colors.push(color);
//         reorderKeys = keys.slice();
//         reorderColors = colors.slice();
//         keyIndex = clickedKeys.indexOf(key);
//         clickedKeys.splice(keyIndex, 1);
//         reOrderKeys();
//         reOrderColors()
//     }else{
//         let keyIndex = keys.indexOf(key);
//         clickedKeys.push(key);
//         keys.splice(keyIndex, 1);
//         colors.splice(keyIndex, 1);
//     }

//     reDrawGraph()
// }

// function reOrderKeys(){

//   reorderKeys.sort((a,b)=>{
//       return keysOrder.indexOf(a) - keysOrder.indexOf(b);
//   });
//   keys = reorderKeys.slice();
//   return keys;
// }

// function reOrderColors(){

//   reorderColors.sort((a,b)=>{
//       return colorsOrder.indexOf(a) - colorsOrder.indexOf(b);
//   });
//   colors = reorderColors.slice();

//   return colors;
// }

// function reDrawGraph(){
//   createStack();
//   drawChart();
// }

  


}
