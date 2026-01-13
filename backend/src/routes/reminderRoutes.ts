import { Router } from "express";
import { deleteReminder, updateReminder } from "../controllers/reminderController";
import { authMiddleware } from "../middlewares/authMiddleware";

const router = Router();

router.use(authMiddleware);

router.put("/:id", updateReminder);
router.delete("/:id", deleteReminder);

export default router;
