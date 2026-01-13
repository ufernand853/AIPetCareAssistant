import type { PetDocument } from "../models/Pet";

const bodyConditions = ["underweight", "ideal", "overweight", "unknown"] as const;
const coatConditions = ["normal", "dry", "shedding", "unknown"] as const;
const dogBreeds = [
  "Labrador Retriever mix",
  "German Shepherd mix",
  "Golden Retriever mix",
  "Border Collie mix",
  "Pit Bull mix",
] as const;
const catBreeds = [
  "Domestic Shorthair",
  "Domestic Longhair",
  "Siamese mix",
  "Maine Coon mix",
  "Tabby mix",
] as const;

export interface AnalysisResult {
  bodyCondition: (typeof bodyConditions)[number];
  coatCondition: (typeof coatConditions)[number];
  breedGuess: string;
  appearanceNotes: string;
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
  const breedGuess =
    pet.species === "dog"
      ? dogBreeds[Math.floor(Math.random() * dogBreeds.length)]
      : catBreeds[Math.floor(Math.random() * catBreeds.length)];
  const appearanceNotes =
    pet.species === "dog"
      ? "Ears appear moderately upright, with a balanced muzzle and a medium-length coat."
      : "Coat appears plush with a rounded face and alert, forward-facing ears.";

  return {
    bodyCondition,
    coatCondition,
    breedGuess,
    appearanceNotes,
    summaryText: `Based on the provided photo for ${pet.name}, the pet looks like a ${breedGuess}. The body condition appears ${bodyCondition}, and the coat looks ${coatCondition}.`,
    recommendations:
      "Provide balanced nutrition, regular exercise, and routine grooming. If the breed guess is important for care decisions, confirm with a DNA test or veterinarian. Consult a veterinarian if you notice rapid changes or health concerns.",
  };
};

// TODO: Replace this stub with a real CV model or external AI service integration.
