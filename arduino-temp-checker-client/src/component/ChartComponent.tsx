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

type MockDataType = {
  min: number;
  max: number;
  date: string;
};

const data = [
  { min: -12, max: 18, date: "2026-09-15" },
  { min: 10, max: 16, date: "2026-09-16" },
  { min: 11, max: 19, date: "2026-09-17" },
  { min: -9, max: 15, date: "2026-09-18" },
  { min: 13, max: 21, date: "2026-09-19" },
  { min: 14, max: 30, date: "2026-09-20" },
  { min: 12, max: 20, date: "2026-09-21" },
  { min: -10, max: 17, date: "2026-09-22" },
];

const Typed = createHorizontalChart<MockDataType, string, number>()({
  XAxis,
  YAxis,
  Tooltip,
  Line,
});

const ChartComponent = () => {
  return (
    <div className="chartWrapper">
      <div className="chart">
        <h3>This weeks min and max temperatures</h3>
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
              right: 0,
              left: 0,
              bottom: 5,
            }}
          >
            <CartesianGrid stroke="rgb(100, 100, 100)" />
            <ReferenceLine y={0} stroke="rgb(0, 174, 255)" strokeWidth={2} />
            <Typed.XAxis dataKey="date" stroke="rgb(228, 129, 0)" />
            <Typed.YAxis
              width="auto"
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
            <Legend stroke="rgb(228, 129, 0)" />
            <Typed.Line
              dataKey="min"
              fill="rgb(0, 195, 255)"
              stroke="rgb(0, 17, 167)"
              strokeWidth={2}
            />
            <Typed.Line
              dataKey="max"
              fill="rgb(0, 0, 0)"
              stroke="rgb(255, 0, 0)"
              strokeWidth={2}
            />
          </Typed.LineChart>
        </div>
      </div>
    </div>
  );
};

export default ChartComponent;
