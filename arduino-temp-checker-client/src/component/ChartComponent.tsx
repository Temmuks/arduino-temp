import { useEffect, useState } from "react";
import {
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  ReferenceLine,
  Tooltip,
  Legend,
  createHorizontalChart,
} from "recharts";
import type { TempType } from "../types/TempType";
import ApiFetch from "../api/ApiFetch";

const Typed = createHorizontalChart<TempType, string, number>()({
  XAxis,
  YAxis,
  Tooltip,
  Line,
});

const ChartComponent = () => {
  const [data, setData] = useState<TempType[]>([]);

  useEffect(() => {
    const fetchChartData = async () => {
      const response = await ApiFetch.getChartData();
      if (response) {
        setData(response);
      }
    };

    fetchChartData();
  }, []);

  return (
    <div className="chartWrapper">
      <div className="chart">
        <h3>The past seven days min and max temperatures</h3>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            width: "100%",
          }}
        >
          <Typed.LineChart
            style={{
              width: "100%",
              maxWidth: "700px",
              height: "100%",
              maxHeight: "70vh",
              aspectRatio: 1.618,
            }}
            responsive
            data={data}
            margin={{
              top: 5,
              right: 70,
              left: 60,
              bottom: 60,
            }}
          >
            <CartesianGrid stroke="rgb(100, 100, 100)" />
            <ReferenceLine y={0} stroke="rgb(0, 174, 255)" strokeWidth={2} />
            <Typed.XAxis
              dataKey="date"
              stroke="rgb(228, 129, 0)"
              interval={0}
              tick={{ angle: -45, textAnchor: "end" }}
            />
            <Typed.YAxis
              width={50}
              domain={[-30, 30]}
              stroke="rgb(228, 129, 0)"
            />
            <Tooltip
              contentStyle={{
                backgroundColor: "#16171d",
              }}
              cursor={{
                fill: "rgba(255,255,255,0.05)",
              }}
            />
            <Legend stroke="rgb(228, 129, 0)" verticalAlign="top" />
            <Typed.Line
              dataKey="min"
              fill="rgb(0, 195, 255)"
              stroke="rgb(0, 119, 255)"
              strokeWidth={2}
            />
            <Typed.Line
              dataKey="max"
              fill="rgb(0, 0, 0)"
              stroke="rgb(255, 0, 0)"
              strokeWidth={2}
            />
            <Typed.Line
              dataKey="average"
              fill="rgb(0, 0, 0)"
              stroke="rgb(255, 208, 0)"
              strokeWidth={2}
            />
          </Typed.LineChart>
        </div>
      </div>
    </div>
  );
};

export default ChartComponent;
