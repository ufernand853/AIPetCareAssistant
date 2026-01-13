import type { Response } from "express";
import type { AuthenticatedRequest } from "../middlewares/authMiddleware";
import { Analysis } from "../models/Analysis";
import { Pet } from "../models/Pet";
import { analyzePetImage } from "../services/AnalysisService";

export const uploadPhotoAndAnalyze = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const pet = await Pet.findOne({ _id: req.params.id, ownerId: req.userId });
  if (!pet) {
    res.status(404).json({ message: "Pet not found" });
    return;
  }

  if (!req.file) {
    res.status(400).json({ message: "Image file is required" });
    return;
  }

  const analysisResult = await analyzePetImage(req.file.path, pet);
  const analysis = await Analysis.create({
    petId: pet.id,
    ownerId: req.userId,
    imageUrl: req.file.path,
    ...analysisResult,
  });

  pet.photoUrl = req.file.path;
  await pet.save();

  res.status(201).json(analysis);
};

export const listAnalyses = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const analyses = await Analysis.find({
    petId: req.params.id,
    ownerId: req.userId,
  }).sort({ createdAt: -1 });
  res.json(analyses);
};

export const getAnalysis = async (
  req: AuthenticatedRequest,
  res: Response
): Promise<void> => {
  const analysis = await Analysis.findOne({
    _id: req.params.analysisId,
    ownerId: req.userId,
  });
  if (!analysis) {
    res.status(404).json({ message: "Analysis not found" });
    return;
  }
  res.json(analysis);
};
