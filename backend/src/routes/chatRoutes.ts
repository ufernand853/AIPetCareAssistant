import { Router } from "express";
import { chat } from "../controllers/chatController";
import { authMiddleware } from "../middlewares/authMiddleware";

const router = Router();

router.post("/", authMiddleware, chat);

export default router;
