import "./App.css";
import ChartComponent from "./component/ChartComponent";
import TempComponent from "./component/TempComponent";

function App() {
  return (
    <>
      <h1>Temperature stats</h1>
      <TempComponent />
      <ChartComponent />
    </>
  );
}

export default App;
