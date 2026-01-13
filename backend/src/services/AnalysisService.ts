import type { PetDocument } from "../models/Pet";

const bodyConditions = ["underweight", "ideal", "overweight", "unknown"] as const;
const coatConditions = ["normal", "dry", "shedding", "unknown"] as const;

export interface AnalysisResult {
  bodyCondition: (typeof bodyConditions)[number];
  coatCondition: (typeof coatConditions)[number];
  summaryText: string;
  recommendations: string;
}

export const analyzePetImage = async (
  imagePath: string,
  pet: PetDocument
): Promise<AnalysisResult> => {
  const bodyCondition =
    bodyConditions[Math.floor(Math.random() * bodyConditions.length)];
  const coatCondition =
    coatConditions[Math.floor(Math.random() * coatConditions.length)];

  return {
    bodyCondition,
    coatCondition,
    summaryText: `Based on the provided photo for ${pet.name}, the body condition appears ${bodyCondition} and the coat looks ${coatCondition}.`,
    recommendations:
      "Provide balanced nutrition, regular exercise, and routine grooming. Consult a veterinarian if you notice rapid changes or health concerns.",
  };
};

// TODO: Replace this stub with a real CV model or external AI service integration.
