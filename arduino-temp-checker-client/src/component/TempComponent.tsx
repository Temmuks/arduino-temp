import { useEffect, useState } from "react";
import { useWebSocket } from "../config/WebSocketContext";
import type { IMessage } from "@stomp/stompjs";

const TempComponent = () => {
  const client = useWebSocket();
  const [temp, setTemp] = useState(0);
  const [time, setTime] = useState<String>();

  const handleTempMessage = (message: IMessage) => {
    console.log("Received:", message.body);

    const d = new Date(Date.now());

    const h = d.getHours().toString();
    const m = d.getMinutes().toString();

    const updateTime = h + ":" + m;
    setTime(updateTime);
    setTemp(JSON.parse(message.body).temp);
  };

  useEffect(() => {
    if (!client) {
      return;
    }

    const subscription = client.subscribe("/topic/temp", handleTempMessage);

    return () => {
      subscription.unsubscribe();
    };
  }, [client]);

  return (
    <div>
      <h1>The current temperature is:</h1>
      <h3>Latest update: {time}</h3>
      <h2>{temp} °C</h2>
    </div>
  );
};

export default TempComponent;
