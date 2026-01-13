import { Router } from "express";
import {
  createPet,
  deletePet,
  getPet,
  listPets,
  updatePet,
} from "../controllers/petController";
import {
  getAnalysis,
  listAnalyses,
  uploadPhotoAndAnalyze,
} from "../controllers/analysisController";
import { listReminders, createReminder } from "../controllers/reminderController";
import { authMiddleware } from "../middlewares/authMiddleware";
import { upload } from "../middlewares/upload";

const router = Router();

router.use(authMiddleware);

router.get("/", listPets);
router.post("/", createPet);
router.get("/:id", getPet);
router.put("/:id", updatePet);
router.delete("/:id", deletePet);

router.post("/:id/photo", upload.single("image"), uploadPhotoAndAnalyze);
router.get("/:id/analyses", listAnalyses);
router.get("/analyses/:analysisId", getAnalysis);

router.get("/:id/reminders", listReminders);
router.post("/:id/reminders", createReminder);

export default router;
