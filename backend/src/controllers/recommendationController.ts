import type { Request, Response } from "express";
import { ProductRecommendation } from "../models/ProductRecommendation";

export const listRecommendations = async (
  req: Request,
  res: Response
): Promise<void> => {
  const { species, targetCondition } = req.query as {
    species?: string;
    targetCondition?: string;
  };

  const query: Record<string, string> = {};
  if (species) {
    query.species = species;
  }
  if (targetCondition) {
    query.targetCondition = targetCondition;
  }

  const recommendations = await ProductRecommendation.find(query).limit(50);
  res.json(recommendations);
};
