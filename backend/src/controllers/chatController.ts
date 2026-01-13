import type { Response } from "express";
import type { AuthenticatedRequest } from "../middlewares/authMiddleware";
import { Pet } from "../models/Pet";
import { sendChatMessage } from "../services/ChatService";

export const chat = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const { petId, message } = req.body as {
    petId?: string;
    message?: string;
  };

  if (!message) {
    res.status(400).json({ message: "Message is required" });
    return;
  }

  const pet = petId
    ? await Pet.findOne({ _id: petId, ownerId: req.userId })
    : null;

  const response = await sendChatMessage(message, pet);
  res.json({
    reply: response.content,
    disclaimer:
      "This is not a veterinary diagnosis. Consult a veterinarian for medical concerns.",
  });
};
