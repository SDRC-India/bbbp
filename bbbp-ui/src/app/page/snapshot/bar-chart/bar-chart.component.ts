import { Component, OnInit, Input, ElementRef, ViewChild, ViewEncapsulation } from '@angular/core';
import * as d3 from 'd3';
declare var $: any;

@Component({
  selector: 'sdrc-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class BarChartComponent implements OnInit {

  @ViewChild('barChart') private chartContainer: ElementRef;
  @Input('data') private data: Array<any>;

  constructor(private hostRef: ElementRef) { }

  ngOnInit() {
    if (this.data) {
      this.createChart(this.data);
    }
  }

  createChart(data) {
    let el = this.chartContainer.nativeElement;
    d3.select(el).select("svg").remove();
    var n = data.length, // number of layers
      m = 10 // number of samples per layer
    var layers = data;
    layers.forEach(function (layer, i) {
      layer.forEach(function (el, j) {
        el.y = undefined;
        el.y0 = i;
      });
    });

    var margin = {
      top: 20,
      right: 20,
      bottom: 30,   // bottom height
      left: 40
    }, width = $(this.hostRef.nativeElement).parent().width() - margin.left - margin.right, height = $(this.hostRef.nativeElement).parent().height() - margin.top - margin.bottom // //
      // height
      - margin.top - margin.bottom + 10;

    var x = d3.scaleBand().domain(data[0].map(function (d) {
      return d.axis;
    })).range([0, width]).padding(0.1);
    var max = d3.max(data[0].map(function (d) {
      return d.value;
    }));
    console.log(max)
    var y = d3.scaleLinear().domain([0, (max + (100 - max % 100))]).rangeRound(
      [height, 0]);

    var color = ["#234a7c", "#006ece", "#6ab7f9"];

    var hoverColor = ["#234a7c", "#006ece", "#6ab7f9"];

    var formatTick = function (d) {
      return d.split(".")[0];
    };
    const xBandwidth = x.bandwidth() > 70 * data.length ? 70 * data.length : x.bandwidth();


    var xAxis = d3.axisBottom().scale(x).tickFormat(formatTick);
    var svg = d3.select(el).append("svg").attr("id",
      "columnbarChart").attr("width",
        width + margin.left + margin.right).attr("height",
          height + margin.top + margin.bottom).append("g")
      .attr(
        "transform",
        "translate(" + margin.left + ","
        + margin.top + ")");

    var layer = svg.selectAll(".layer").data(layers).enter()
      .append("g").attr("class", "layer").style("fill",
        function (d, i) {
          return color[i];
        }).attr("id", function (d, i) {
          return i;
        });
    let allNullValues;
    for (let j = 0; j < data.length; j++) {
      for (let index = 0; index < data[j].length; index++) {
        if (data[j][index].value != null) {
          allNullValues = false;
          break;
        }
      }
    }
    if (allNullValues == false) {
      var rect = layer.selectAll("rect").data(function (d) {
        return d;
      }).enter().append("rect").attr("x", function (d) {
        return x(d.axis) + (x.bandwidth() - xBandwidth) / 2;
      }).attr("y", height).attr("width", xBandwidth).attr(
        "height", 0).attr("class", function (d, i) {
          return d.cssClass;
        }) .style("cursor", (d) => {
          if (d.value) {
            return 'pointer'
          }
        })
        .on("mouseover", function (d) {
          showPopover.call(this, d);
        }).on("mouseout", function (d) {
          removePopovers();
        });
    } else {
      if (width <= 360) {
        svg.append("text")
            .attr("transform", "translate("+ width/2 +",0)")
            .attr("x", 0)
            .attr("y",30)
            .attr("font-size", "16px")
            .style("text-anchor", "middle")
            .text("Data Not Available");
      } else {
        svg.append("text")
            .attr("transform", "translate("+ width/2 +",0)")
            .attr("x", 0)
            .attr("y",30)
            .attr("font-size", "16px")
            .style("text-anchor", "middle")
            .text("Data Not Available");
      }
    }
    svg.append("g").attr("class", "x axis").attr("transform",
      "translate(0," + height + ")").call(xAxis)
      .selectAll("text").style("text-anchor", "middle")
      .attr("class", function (d, i) { return "evmbartext" + i })
      .attr("dx", "-.2em").attr("dy", ".70em").call(wrap, x.bandwidth(), width);

    // svg.append("text").attr("class", "x axis").attr("transform",
    //     "translate(0," + height + ")").attr(
    //     "x", width / 2).attr("y", margin.bottom).attr("dx",
    //     "1em").style("text-anchor", "middle").text(
    //     "Time Period");


    var yAxis = d3.axisLeft().scale(y);

    svg.append("g").attr("class", "y axis").call(yAxis).append(
      "text").attr("transform", "rotate(-90)").attr("y",
        0 - margin.left).attr("x", 0 - (height / 2)).attr(
          "dy", "1em").attr("text-anchor", "end")
    // .text(
    // "Score(%)");

    // svg.append("text")
    // .attr("class", "y axis")
    // .attr("text-anchor", "end")
    // .attr("y", 6)
    // .attr("dy", "1em")
    // .attr("transform", "rotate(-90)")
    // .text("Score(%)");

    function transitionGrouped() {
      y.domain([0, (max + (100 - max % 100))]);

      rect.transition().duration(500).delay(function (d, i) {
        return i * 10;
      }).attr("x", function (d, i, j) {
        return x(d.axis) + (x.bandwidth() - xBandwidth) / 2 + xBandwidth / n * d.y0; // function(d)     
      }).attr("width", xBandwidth / n).transition().attr(
        "y", function (d) {
          return y(d.value);
        }).attr("height", function (d) {
          return height - y(d.value);
        });
    }

    transitionGrouped();
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
            if (d.axis != "")
              return "<div style='color: #257ab6;'>" + d.axis + "</div>" + "Score : "
                + d.value + "%";
            else
              return "<div style='color: #257ab6;'> Average: " + d.value + "%</div>";
          }
        });
      $(this).popover('show');
    }

     //============Text wrap function in x-axis of column chart=====================
     function wrap(text, width, windowWidth) {
      text.each(function () {       
        var text = d3.select(this),
          words = text.text().split(/\s+/).reverse(),
          word,
          cnt = 0,
          line = [],
          lineNumber = 0,
          lineHeight = 1.1,
          y = text.attr("y"),
          dy = parseFloat(text.attr("dy"));     
          if(windowWidth > 660)  
          var tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em").style('font-size','10px');   
          else   
          var tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em").style('font-size','7px');   

        if (words.length == 1) {
          let chars = words.toString().split("");
          chars.splice((chars.length / 2).toFixed(), 0, '-', ' ');
          tspan.text(chars.join(""));
          if (tspan.node().getComputedTextLength() > width) {
            words = chars.join("").split(/\s+/).reverse();
          }
          tspan.text('');
        }
        while (word = words.pop()) {
          cnt++;
          line.push(word);
          tspan.text(line.join(" "));
          if (tspan.node().getComputedTextLength() > width) {
            line.pop();
            tspan.text(line.join(" "));
            line = [word];
            // if(cnt!=1)
            if(width > 660)
            tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").style('font-size','10px').text(word);
            else 
            tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").style('font-size','7px').text(word);
          }
        }
      });
    }


    //NEW CODE FOR DATA VALUE TEXT ON EACH BAR-----------------
    var e0Arr = [];
    for (var i = 0; i < data.length; i++) {
      e0Arr.push(data[i][0].value);
      layer.selectAll("evmbartext" + i).data(data[i]).enter()
        .append("text").attr(
          "x",
          function (d) {
            // return x(d.axis) + (x.bandwidth()- xBandwidth)/2+ xBandwidth
            //         / 2 + 12;
            return x(d.axis) + (x.bandwidth() - xBandwidth) / 2 + xBandwidth / (2 * data.length) + (xBandwidth / data.length * i);
          })
        .attr("y", function (d) {
          return y(d.value) - 3;
        }).style("text-anchor", "middle").style("fill",
          "#000000").text(function (d) {
            return Math.round(d.value);
          });
    }
  }

}
