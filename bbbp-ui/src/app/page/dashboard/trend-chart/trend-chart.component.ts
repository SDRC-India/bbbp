import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import * as d3 from 'd3v4';

import { DashboardService } from '../services/dashboard.service';

declare var jquery:any;
declare var $ :any;

@Component({
  selector: 'app-trend-chart',
  templateUrl: './trend-chart.component.html',
  styleUrls: ['./trend-chart.component.scss']
})
export class TrendChartComponent implements OnInit {

  @ViewChild('linechart') private chartContainer: ElementRef;

//  @Input() private data: LineChartModel[];
  @Input() private data: any;



  constructor(private dashboardService: DashboardService) { }

  ngOnInit() {
    if(this.data){
      this.createChart(this.data);
    }
  }

ngOnChanges(changes){
  if(this.data && changes.data.previousValue){
    this.createChart(this.data);
  }
}

createChart(data){
  let el = this.chartContainer.nativeElement;
  d3.select(el).selectAll("*").remove();
  var margin = {
    top : 20,
		right : 15,
		bottom : 80,
		left : 50
  },width
  if($(window).width() > 565){
    width = 565;
  }
  else{
    width = $(window).width() - (margin.left + margin.right);
  }
  if($(window).width() > 565)
  var height = 350	- margin.top - margin.bottom;
else
 var height=250- margin.top - margin.bottom;

// set the ranges
// var x = d3.scale.ordinal().rangeRoundBands(
// 		[ 0, width ], 1.0);
// var y = d3.scale.linear().range(
// 		[ height, 0 ]);
// parse the date / time
var x = d3.scaleBand().range([0, width-margin.left - margin.right], 1.0);
var y = d3.scaleLinear().rangeRound(
   [ height - margin.top, 0 ]);

// define the axis
// var xAxis = d3.svg.axis().scale(x).orient("bottom")
// 		.ticks(5);
// var yAxis = d3.svg.axis().scale(y).orient("left")
// 		.ticks(5);
var xAxis = d3.axisBottom().scale(x).ticks(5);
var yAxis = d3.axisLeft().scale(y)
.ticks(5);
// Nest the entries by symbol
var dataNest = d3.nest().key(function(d) {
 return d.key;
}).entries(data);

var max = d3.max(data.map(function (d) {
  return  parseFloat(d.value);
}));

if (max < 100) {
  max = 100;
}

// // Define the line
// var curveStructure = d3.curveCardinal;
var lineFunctionCardinal = d3.line()
 .defined(function(d) {  return d && d.value!= null; })
 .x(function(d) {
   
   return x(d.date)+width/data.length * dataNest.length / 2;
 }).y(function(d) {
   return y(d.value);
 }).curve(d3.curveCardinal);
var lineFunctionStep = d3.line()
 .defined(function(d) {  return d && d.value!= null; })
 .x(function(d) {
   
   return x(d.date)+width/data.length * dataNest.length / 2;
 }).y(function(d) {
   return y(d.value);
 }).curve(d3.curveStepBefore);

y.domain([ 0, max ]);

// Adds the svg canvas
var svg = d3.select(el).append("svg").attr("id",
   "trendsvg").attr("width",
   width).attr(
   "height",
   height + 70)
   .append("g").attr(
       "transform",
       "translate(" + margin.left + ","
           + (margin.top ) + ")").style(
       "fill", "#FFF");

// Get the data
//					lineData.forEach(function(d) {
//						d.date = d.date;
//						d.value = +d.value;
//					});
x.domain(data.map(function(d) {
 return d.date;
}));
if(this.dashboardService.submittedSector.sectorId == null){
  y.domain([ 800, 1000 ]);
}
else{

  if(this.dashboardService.submittedIndicator.unitName == 'PERCENT'){
    y.domain([ d3.min(data, function(d) {
      return d.value - d.value%10;
     }), max ]);
  }else{
    y.domain([ d3.min(data, function(d) {
      return d.value - d.value%10;
     }), d3.max(data, function(d) {
     return d.value - d.value%10 + 10;
    }) ]);
  }


  

}

// y.domain(d3.extent(data, function(d) {
//   return d.value - d.value%10;
//  }) );

// Loop through each symbol / key
//var color = d3.scale.category10(); 
var color = d3.scaleOrdinal().range(
    [ "#1f4a7c", "#f07258", "#333a3b", "#428ead"]);

//============Text wrap function in x-axis of column chart=====================
function wrap(text, width) {
   text.each(function() {
     var text = d3.select(this),
         words = text.text().split(/\s+/).reverse(),
         word,
         cnt=0,
         line = [],
         lineNumber = 0,
         lineHeight = 1.1, 
         x = text.attr("x"),
         y = text.attr("y"),
         dy = parseFloat(text.attr("dy")),
         tspan = text.text(null).append("tspan").attr("x", x).attr("y", y).attr("dy", dy + "em");
     while (word = words.pop()) {
       cnt++;
       line.push(word);
       tspan.text(line.join(" "));
       if (tspan.node().getComputedTextLength() > width) {
         line.pop();
         
         tspan.text(line.join(" "));	
         line = [word];
         if(cnt!=1)
         tspan = text.append("tspan").attr("x", x).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
       }
     }
   });
 }

// Add the X Axis
// svg.append("g").attr("class", "x axis").attr(
// 		"transform", "translate(0," + height + ")")
// 		.call(xAxis).append("text").attr("x",
// 				"405").attr("y",
// 				"45").attr("dx", ".71em")												
// 		.call(wrap, x.bandwidth());
svg.append("g").attr("class", "x axis")
.attr(
   "transform", "translate(0," + (height - margin.top) + ")")
   .call(xAxis)
   .append("text").attr("x",
       width-30).attr("y",
       "60").attr("dx", ".1em")																			
   .style("fill", "#FFF")
   .text(() => {
     if(width > 600){
       return "Time Period"
     }
     else{
       return "";
     }
   })
d3.selectAll(".line-chart .x.axis .tick text").attr("dx", ()=>{
  if(this.dashboardService.submittedSector.sectorId == null){
    return '-3em'
  }
  else{
    return "-5.3em";
  }
  }).attr("dy",
   "0").attr("transform", function(d) {
 return "rotate(-30)";
}).style({"text-anchor":
"end","font-size":"11px","font-weight":"normal"});

/*if($(window).width()<900){
d3.selectAll(".tick text").style({"text-anchor":
   "end","font-size":"11px","font-weight":"normal"}).attr("dx", "-.3em").attr("dy",
   "1em").attr("transform", function(d) {
 return "rotate(-45)";
});
}
else{
 d3.selectAll(".tick text").style({"text-anchor":
   "end","font-size":"12px","font-weight":"normal","fill":"#333a3b"}).attr("dx", "0").attr("dy",
   "1em").attr("transform", function(d) {
 return "rotate(-45)";
});
 
}*/

// xsvg;
// Add the Y Axis

 svg.selectAll("text");
 svg.append("g").attr("class", "y axis").call(yAxis)
 .append("text").attr("transform",
     "rotate(-90)").attr("y", -40).attr("x", -5).attr(
     "dy", ".71em")
     .style("fill", "#FFF")
     .text(()=>{
       if(this.dashboardService.submittedSector.sectorId == null)
         return "Girls per 1000 Boys"
        else{
          return "Value ( in " + this.dashboardService.submittedIndicator.unitName.toLowerCase() + ")"
        }
        })
     .style({"text-anchor":
     "middle", "fill": "#FFF",
       "font-weight": "bold",
       "letter-spacing": "1px"
     });

     //check for no data availble
     let allNullValues = true;
     for (let j = 0; j < data.length; j++) {
         if (data[j].value != null) {
           allNullValues = false;
           break;
         }
     }
     if (allNullValues) {
         svg.append("text")
           .attr("transform", "translate("+ width/2 +",0)")
           .attr("x", 0)
           .attr("y",30)
           .attr("font-size", "16px")
           .style("text-anchor", "middle")
           .text("Data Not Available");

           return;
       }
// adding multiple lines in Line chart
for (var index = 0; index < dataNest.length; index++) {

 var series = svg.append(
     "g").attr("class", "series tag"+ dataNest[index].key.split(" ")[0]).attr("id",
     "tag" + dataNest[index].key.split(" ")[0]);

 var path = svg.selectAll(".series#tag"+dataNest[index].key.split(" ")[0])
     .append("path")
     .attr("class", "line tag"+dataNest[index].key.split(" ")[0])
     .attr("id", "tag" + dataNest[index].key.split(" ")[0])
     .attr(
         "d",
         function(d) {
             return lineFunctionCardinal(dataNest[index].values);
           }).style("stroke", function(d) {
       return color(dataNest[index].key);
     }).style("stroke-width", "2px").style(
         "fill", "none").style("cursor", function(d){
           if(dataNest[index].key == "P-Average")
             return "pointer";
           else
             return "default";
             }).on("mouseover",
                 function(d) {
               if($(this).attr("id") == "tagP-Average")
                 showPopover.call(this, dataNest[3].values[0]);
             }).on("mouseout", function(d) {
           removePopovers();
         });			;
//						 var totalLength = path.node().getTotalLength();
//
//					        path
//					          .attr("stroke-dasharray", totalLength + " " + totalLength)
//					          .attr("stroke-dashoffset", totalLength)
//					          .transition()
//					            .duration(3000)
//					            .ease("linear")
//					            .attr("stroke-dashoffset", 0);
       
       
 svg.selectAll(".series#tag"+dataNest[index].key.split(" ")[0]).select(".point").data(function() {
   return dataNest[index].values;
 }).enter().append("circle").attr("id",
     "tag" + dataNest[index].key.split(" ")[0]).attr(
     "class", function(d){
         return "point tag"+ dataNest[index].key.split(" ")[0]
       }).attr("cx",
     function(d) {
       return x(d.date)+width/data.length * dataNest.length / 2;
     }).attr("cy", function(d) {
   return y(d.value);
 }).attr("r",  function(d) {
  if(d.value!=null)
    return "3px";
}).style("fill", function(d) {
     return color(dataNest[index].key);
 }).style("stroke", "none").style(
     "stroke-width", "2px").style("cursor", "pointer").on("mouseover",
     function(d) {
       // d3.select(this).moveToFront();
       showPopover.call(this, d);
     }).on("mouseout", function(d) {
   removePopovers();
 });			
 
 // second render pass for the dashed lines
 var left, right
 for (var j = 0; j < dataNest[index].values.length; j += 1) {
     var current = dataNest[index].values[j]
     if (current.value!=null) {
       left = current
     } else {
       // find the next value which is not null
       while (dataNest[index].values[j]!=undefined && dataNest[index].values[j].value == null && j < dataNest[index].values.length) j += 1
       right = dataNest[index].values[j]
       
       if(left!=undefined && right!=undefined && left.key == right.key){
          svg.append("path")
           .attr("id", "tag" + dataNest[index].key)
           .attr("class", "tag" + dataNest[index].key)
           .attr("d", lineFunctionCardinal([left, right]))
             .style("stroke", color(dataNest[index].key))
             .attr('stroke-dasharray', '5, 5').style(
               "fill", "none");
       }
      
         

       j -= 1
     }
   }
 
}
/*
* switch button for series line chart
*/
/*	*/

svg.append("text").attr("x", width / 2)// author
.attr("y", height + 95).attr("dy", ".3em")
   .text(dataNest[0].values[0].name).call(wrap, width)
   .style("fill", "#FFF").style("font-weight", "bold")
    .style("text-anchor", "middle").style("font-size", "13px")
                    

function removePopovers() {
 $('.popover').each(function() {
   $(this).remove();
 });
}
function showPopover(d) {
 $(this).popover(
     {
       title : '',
       placement : 'top',
       container : 'body',
       trigger : 'manual',
       html : true,
       animation: false,
       content : function() {
         return d.key == "P-Average" ? "P-Average: <span class='navy-text'>"+d.value : "</span>Time Period: <span class='navy-text'>" + d.date
             + "</span><br/>"+ (d.key == "CL" ? "Fractional Index" : d.key)+": <span class='navy-text'>"
             + d.value +"</span>" + (d.pdsas ? "<br>PDSA:<span class='navy-text'> " + d.pdsas + "</span>":"");
       }
     });
 $(this).popover('show');
 // $('.popover.fade.top.in').css('top', parseFloat($('.popover.fade.top.in').css('top').slice(0, -2)) + $(window).scrollTop());
}
/*//Draw a horizontal line for overall score of latest time period
svg.append("g").attr("class", "y axis").call(yAxis).append("line")          // attach a line
 .style("stroke", "#428ead") 		// colour the line
 .attr("stroke-width", 1)
 .attr("fill", "none")
 .attr("x1", 0)     				// x position of the first end of the line
 .attr("y1", y("30"))      // y position of the first end of the line
 .attr("x2", width)     				// x position of the second end of the line
 .attr("y2", y("30"))
 .style("cursor", "pointer").on("mouseover", function(d) {
 showPopover.call(this, {axis: "", value: Math.round("30")}
       );})    // y position of the second end of the line
.on("mouseout", function() {
removePopovers();
});
*/					d3.selection.prototype.moveToFront = function() {
 return this.each(function(){
     this.parentNode.appendChild(this);
   });
};
d3.selectAll(".domain, .y.axis .tick line").style({"fill": "none", "stroke": "#FFF"});
d3.selectAll("circle.point").moveToFront();
}

}
