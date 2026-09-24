import type { TempType } from "../types/TempType";

const apiUrl = import.meta.env.VITE_API_URL ?? "";

const getChartData = async () => {
  const response = await fetch(`${apiUrl}/temp/chartdata`);
  const data: TempType[] = await response.json();

  return data;
};

export default {
  getChartData,
};
