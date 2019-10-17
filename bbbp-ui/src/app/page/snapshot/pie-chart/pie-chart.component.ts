import { Component, OnInit, Input, ViewEncapsulation, ElementRef, ViewChild} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import * as d3 from "d3";

declare var $ :any;


@Component({
  selector: 'sdrc-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PieChartComponent implements OnInit {
  @ViewChild('pieChart') private chartContainer: ElementRef;
  @Input() private data : any;

  constructor(private httpClient: HttpClient, private hostRef: ElementRef) { }

  ngOnInit() {
    if(this.data){
      this.createChart(this.data);
    }
  }

  createChart(data){
    //console.log(data);
    let el = this.chartContainer.nativeElement;     
    d3.select(el).selectAll("*").remove();
    let margin = {
        left: 20,
        right: 20,
        top: 0,
        bottom: 20
    }
    
      var width = 150;
      var height = 150;
      margin.left = ($(this.hostRef.nativeElement).parent().width() - width) / 2 ; 
      margin.right = ($(this.hostRef.nativeElement).parent().width() - width) / 2 ;  
      var radius = Math.min(width, height) / 2;
      
  var arc = d3.arc()
    .outerRadius(
      radius - 10)
    .innerRadius(0);

  var pie = d3.pie()
    .value(
      function (d) {
        return parseInt(d.value);
      }).sort(
        null);


    var svg = d3
      .select(el)
      .append("svg")
      .attr("id",
        "chart")
      .attr(
        "width",
        width)
      .attr(
        "height",
        height)
        .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')')
      .append("g")
      .attr(
        "transform",
        "translate("
        + (width / 2 - 10)
        + ","
        + (height / 2)
        + ")");


  // ////===========End of
  // Legend for doughnut
  // chart======================//////

//   var legend = svg.selectAll('g')
//   .attr("class", "legend")
//   .attr("x",width/2)
//   .attr("y",height/4)
//   .data(pie(data))
//   .enter().append("g")
//   .attr("transform", function(d, i) { return "translate(0," + (height/2 - i * 20) + ")"; });

//   legend.append("rect")
//   .attr("width", width/33)
//   .attr("height", width/33)
//   .attr("x",width/3.7)
//   .attr("y",-(height/3))
//   .style("fill", function(d) { return color(d.data.axis); });


// legend.append("text")
//   .attr("x", width/3.3)
//   .attr("y", -(height/3.3)+2)
//   .attr("dy", ".35em")
//   .attr("font-size","1vmax")
//   .text(function(d) { return d.data.axis; });
  

//						  ////===========End of Legend for doughnut chart======================//////	

  function pieChart(data) {
    var g = svg.selectAll(
      ".arc").data(
        pie(data))
      .enter()
      .append("g")
      .attr("class",
        "arc")
      .attr("align",
        "left");

    g
      .append("path")
      .attr("d", arc)
      .attr("class" , "pie-path")
      .style("cursor", (d) => {
        if (d.value) {
          return 'pointer'
        }
      })
      .on("mouseover", function (d, i) {
        showPopover.call(this, d, i);
      })
      .on(
        "mouseout",
        function removePopovers() {
          $(
            '.popover')
            .each(
              function () {
                $(
                  this)
                  .remove();
              });
        })
    //   .on("click",
    //     click)
      .transition()
      .delay(
        function (
          d,
          i) {
          return i * 500;
        })
      .duration(500)
      .attrTween('d', function (d) {
            var i = d3.interpolate(d.startAngle + 0.1, d.endAngle);
          return function (t) {
            d.endAngle = i(t);
            return arc(d);
          };
        })
      .attr("class", function (d) {
          if (d.endAngle)
            return d.data.axis;
          else
            return d.data.axis
              + " zeroValue";
        })
      .style("fill", function (d) {
          return d.data ? d.data.colorCode:'#333';
        })
        svg.selectAll(".arc").append("text")
        .attr("transform", function(d) {
            return "translate(" + arc.centroid(d) + ")";
        })
        .attr("dy", "0.5em")
        .text(function(d) {
            return Math.round(d.data.value);
        });;

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
                
                  return "<span style='color:#2f4f4f'>"
                  + d.data.axis
                  + " : </span>"
                  + "<span style='color:#2f4f4f'>"
                  + (d.endAngle
                    - d.startAngle > 0.0 ? d.data.value
                    + '%'
                    : '')
                  + "</span>";
              }
            });
        $(this).popover('show');
      }

      function removePopovers() {
        $('.popover').each(function() {
          $(this).remove();
        });
      }
        

  $(".percentVal")
    .delay(1500)
    .fadeIn();
  }
  
  let allNullValues;
    for (let j = 0; j < data.length; j++) {
      // for (let index = 0; index < data[j].length; index++) {
        if (data[j].value != null) {
          allNullValues = false;
          break;
        }
      // }
    }
    if (allNullValues == false) {
      pieChart(data);
    } else {
      
        svg.append("g").append("text")
          .attr("transform", "translate("+ -(width / 2 - 10) +",0)")
          .attr("x", 0)
          .attr("y", 0)
          .attr("font-size", "16px")
          .text("Data Not Available");
      } 
  



}
}
