import express from "express";
import cors from "cors";
import dotenv from "dotenv";
import morgan from "morgan";
import path from "path";
import authRoutes from "./routes/authRoutes";
import petRoutes from "./routes/petRoutes";
import reminderRoutes from "./routes/reminderRoutes";
import recommendationRoutes from "./routes/recommendationRoutes";
import chatRoutes from "./routes/chatRoutes";
import { connectDatabase } from "./config/db";
import { errorHandler } from "./middlewares/errorHandler";

dotenv.config();

const app = express();

app.use(cors());
app.use(express.json());
app.use(morgan("dev"));

const uploadsPath = path.join(__dirname, "../uploads");
app.use("/uploads", express.static(uploadsPath));

app.get("/", (_req, res) => {
  res.json({ message: "AI Pet Care Assistant API" });
});

app.use("/api/auth", authRoutes);
app.use("/api/pets", petRoutes);
app.use("/api/reminders", reminderRoutes);
app.use("/api/recommendations", recommendationRoutes);
app.use("/api/chat", chatRoutes);

app.use(errorHandler);

const port = process.env.PORT ? Number(process.env.PORT) : 4000;
const mongoUri = process.env.MONGODB_URI;

if (!mongoUri) {
  throw new Error("MONGODB_URI is not configured");
}

connectDatabase(mongoUri)
  .then(() => {
    app.listen(port, () => {
      console.log(`Server running on port ${port}`);
    });
  })
  .catch((error) => {
    console.error("Failed to start server", error);
    process.exit(1);
  });
