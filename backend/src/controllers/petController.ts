import type { Response } from "express";
import { Pet } from "../models/Pet";
import type { AuthenticatedRequest } from "../middlewares/authMiddleware";

export const listPets = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const pets = await Pet.find({ ownerId: req.userId }).sort({ createdAt: -1 });
  res.json(pets);
};

export const createPet = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const pet = await Pet.create({ ...req.body, ownerId: req.userId });
  res.status(201).json(pet);
};

export const getPet = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const pet = await Pet.findOne({ _id: req.params.id, ownerId: req.userId });
  if (!pet) {
    res.status(404).json({ message: "Pet not found" });
    return;
  }
  res.json(pet);
};

export const updatePet = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const pet = await Pet.findOneAndUpdate(
    { _id: req.params.id, ownerId: req.userId },
    req.body,
    { new: true }
  );
  if (!pet) {
    res.status(404).json({ message: "Pet not found" });
    return;
  }
  res.json(pet);
};

export const deletePet = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const pet = await Pet.findOneAndDelete({
    _id: req.params.id,
    ownerId: req.userId,
  });
  if (!pet) {
    res.status(404).json({ message: "Pet not found" });
    return;
  }
  res.json({ message: "Pet deleted" });
};
