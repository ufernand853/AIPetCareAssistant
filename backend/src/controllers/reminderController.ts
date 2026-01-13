import type { Response } from "express";
import type { AuthenticatedRequest } from "../middlewares/authMiddleware";
import { Reminder } from "../models/Reminder";

export const listReminders = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const reminders = await Reminder.find({
    petId: req.params.id,
    ownerId: req.userId,
  }).sort({ dueDate: 1 });
  res.json(reminders);
};

export const createReminder = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const reminder = await Reminder.create({
    ...req.body,
    petId: req.params.id,
    ownerId: req.userId,
  });
  res.status(201).json(reminder);
};

export const updateReminder = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const reminder = await Reminder.findOneAndUpdate(
    { _id: req.params.id, ownerId: req.userId },
    req.body,
    { new: true }
  );
  if (!reminder) {
    res.status(404).json({ message: "Reminder not found" });
    return;
  }
  res.json(reminder);
};

export const deleteReminder = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const reminder = await Reminder.findOneAndDelete({
    _id: req.params.id,
    ownerId: req.userId,
  });
  if (!reminder) {
    res.status(404).json({ message: "Reminder not found" });
    return;
  }
  res.json({ message: "Reminder deleted" });
};
