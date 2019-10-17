import { Component, OnInit, ViewEncapsulation, OnChanges, ViewChild, ElementRef, Input } from '@angular/core';
import * as d3 from 'd3';

declare var $: any;

@Component({
  selector: 'sdrc-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LineChartComponent implements OnInit, OnChanges {

  @ViewChild('linechart') private chartContainer: ElementRef;
  @Input() private data: any;

  constructor(private hostRef: ElementRef) { }

  ngOnInit() {
    if (this.data) {
      this.createChart(this.data);
    }
  }

  ngOnChanges(changes) {
    if (this.data && changes.data.previousValue) {
      this.createChart(this.data);
    }
  }

  createChart(data) {
    let el = this.chartContainer.nativeElement;
    d3.select(el).selectAll("*").remove();
    var margin = {
      top: 20,
      right: 20,
      bottom: 30,
      left: 40
    }
    let w = $(this.hostRef.nativeElement).parent().width();
    let h = $(this.hostRef.nativeElement).parent().height() - 40

    let width = w - margin.left - margin.right
    let height = h - margin.top - margin.bottom




    var x = d3.scaleBand().range([0, width], 1.0);
    var y = d3.scaleLinear().rangeRound(
      [height, 0]);

    // define the axis
    var xAxis = d3.axisBottom().scale(x).ticks(5);
    var yAxis = d3.axisLeft().scale(y)
      .ticks(5);

    var dataNest = d3.nest().key(function (d) {
      return d.key;
    }).entries(data);

    var max = d3.max(data.map(function (d) {
      return  parseFloat(d.value);
    }))
    if (max < 100) {
      max = 100;
    }
    x.domain(data.map(function (d) {
      return d.timeperiod;
    }));
    y.domain([0, max]);

    // // Define the line
    var lineFunctionCardinal = d3.line()
      .defined(function (d) { return d && d.value != null; })
      .x(function (d) {
        return x(d.timeperiod) + width / data.length * dataNest.length / 2;
      }).y(function (d) {
        return y(d.value);
      }).curve(d3.curveCardinal);




    // Adds the svg canvas
    var svg = d3.select(el).append("svg").attr("id",
      "trendsvg").attr("width",
        w).attr(
          "height",
          h)
      .append("g").attr(
        "transform",
        "translate(" + margin.left + ","
        + (margin.top) + ")").style(
          "fill", "#000");



    var color = d3.scaleOrdinal().range(
      ["#1f4a7c", "#f07258", "#333a3b", "#428ead"]);


    // add the x-axis
    svg.append("g").attr("class", "x axis")
      .attr(
        "transform", "translate(0," + height + ")")
      .call(xAxis)
    //  .append("text").attr("x",
    //      width).attr("y",
    //      "65").attr("dx", ".71em")																			

    //  .text("Time Period").style({"fill":
    //      "#000","text-align":"right", "text-anchor": "end",
    //    "font-weight": "bold",
    //    "letter-spacing": "1px"
    //  });
    d3.selectAll(".x.axis .tick text").attr("dx", "0").attr("dy",
      "10").style({
        "text-anchor":
          "middle", "font-size": "11px", "font-weight": "normal"
      });

    svg.selectAll("text");
    svg.append("g").attr("class", "y axis").call(yAxis)
      .append("text").attr("transform",
        "rotate(-90)").attr("y", -50).attr("x", -height / 2).attr(
          "dy", ".71em").text("Value")
      .style({
        "text-anchor":
          "middle", "fill": "#000",
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
    for (let index = 0; index < dataNest.length; index++) {

      var series = svg.append(
        "g").attr("class", "series tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0])).attr("id",
          "tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0]));

      var path = svg.selectAll(".series#tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0]))
        .append("path")
        .attr("class", "line tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0]))
        .attr("id", "tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0]))
        .attr(
          "d",
          function (d) {
            //if(dataNest[index].key == "CL")
            return lineFunctionCardinal(dataNest[index].values);
            //else
            //return lineFunctionStep(dataNest[index].values);
          }).style("stroke", function (d) {
            return color(dataNest[index].key);
          }).style("stroke-width", "2px").style(
            "fill", "none").style("cursor", function (d) {
              //  if(dataNest[index].key == "P-Average")
              //    return "pointer";
              //  else
              return "default";
            }).on("mouseover",
              function (d) {
                if ($(this).attr("id") == "tagP-Average")
                  showPopover.call(this, dataNest[3].values[0]);
              }).on("mouseout", function (d) {
                removePopovers();
              });;


      var totalLength = path.node().getTotalLength();

      path.attr("stroke-dasharray", totalLength + " " + totalLength)
        .attr("stroke-dashoffset", totalLength)
        .transition()
        .duration(3000)
        //  .ease("linear")
        .attr("stroke-dashoffset", 0);
      svg.selectAll(".series#tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0])).select(".point").data(function () {
        return dataNest[index].values;
      }).enter().append("circle").attr("id",
        "tag" + this.removeWhiteSpace(dataNest[index].key.split(" ")[0])).attr(
          "class", (d) => {
            //  if(d.key != "CL" || d.pdsas == "")
            //    return "point tag"+ dataNest[index].key.split(" ")[0]
            //  else
            //    return "point pdsaAvailable tag"+ dataNest[index].key.split(" ")[0]
            return this.removeWhiteSpace(dataNest[index].key.split(" ")[0])
          }).attr("cx",
            function (d) {
              return x(d.timeperiod) + width / data.length * dataNest.length / 2;
            }).attr("cy", function (d) {
              return y(d.value);
            }).attr("r", function (d) {
              if (d.value == null)
                return "0px";
              else
                return "3px";
            }).style("fill", function (d) {
              //if(d.key != "CL" || d.pdsas == "")
              return color(dataNest[index].key);
              //  else
              //    return "#FFC107";
            }).style("stroke", "none").style(
              "stroke-width", "2px").style("cursor", "pointer").on("mouseover",
                function (d) {
                  // d3.select(this).moveToFront();
                  showPopover.call(this, d);
                }).on("mouseout", function (d) {
                  removePopovers();
                });

                // second render pass for the dashed lines
    var left, right
    for (var j = 0; j < dataNest[index].values.length; j += 1) {
      var current = dataNest[index].values[j]
      if (current.value != null) {
        left = current
      } else {
        // find the next value which is not nan
        while (dataNest[index].values[j] != undefined && dataNest[index].values[j].value == null && j < dataNest[index].values.length) j += 1
        right = dataNest[index].values[j]

        if (left != undefined && right != undefined && left.key == right.key) {
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

    

    svg.append("text").attr("x", width / 2)// author
      .attr("y", height + 90).attr("dy", ".3em")
      .text("Time Period")
      .style({
        "fill": "rgb(66, 142, 173)",
        "font-weight": "bold",
        "text-anchor": "middle",
        "font-size": "13px"
      })

    

    function removePopovers() {
      $('.popover').each(function () {
        $(this).remove();
      });
    }
    function showPopover(d) {
      $(this).popover(
        {
          title: '',
          placement: 'top',
          container: 'body',
          trigger: 'manual',
          html: true,
          animation: false,
          content: function () {
            return "Time Period: <span class='navy-text'>" + d.timeperiod
              + "</span><br/>" + "Data Value: <span class='navy-text'>"
              + d.value + "</span><br/>";
          }
        });
      $(this).popover('show');
    }

    d3.selection.prototype.moveToFront = function () {
      return this.each(function () {
        this.parentNode.appendChild(this);
      });
    };
    d3.selectAll(".domain, .y.axis .tick line").style({ "fill": "none", "stroke": "#000" });
    d3.selectAll("circle.point").moveToFront();
  }
  removeWhiteSpace(val: string){
    return val.replace(/\s/g,'').replace(/[({:,?})&/]/g,"");;
  }

}
