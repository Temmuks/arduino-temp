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

    let h = d.getHours().toString();
    let m = d.getMinutes().toString();
    if (d.getMinutes() < 10) {
      m = "0" + d.getMinutes().toString();
    }
    if (d.getHours() < 10) {
      h = "0" + d.getHours().toString();
    }

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
    <div className="divWrapper">
      <div className="box">
        <h3>Current temperature outside</h3>
        <h3>{temp} °C</h3>
        <h4>Latest update: {time}</h4>
      </div>
    </div>
  );
};

export default TempComponent;
