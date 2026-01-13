import { Router } from "express";
import { listRecommendations } from "../controllers/recommendationController";

const router = Router();

router.get("/", listRecommendations);

export default router;
