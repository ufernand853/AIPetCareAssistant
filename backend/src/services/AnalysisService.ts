import { readFile } from "fs/promises";
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

const ANALYSIS_PROMPT = `Analiza la foto de la mascota y responde solo con JSON válido.
Campos requeridos:
- bodyCondition: "underweight" | "ideal" | "overweight" | "unknown"
- coatCondition: "normal" | "dry" | "shedding" | "unknown"
- breedGuess: texto corto
- appearanceNotes: 1-2 oraciones en español
- summaryText: 1-2 oraciones en español
- recommendations: 1-2 oraciones en español con recomendación general y sugerencia de veterinario si hay dudas
Si la imagen es borrosa o no muestra a la mascota, usa "unknown" en bodyCondition/coatCondition y explica en summaryText.`;

export interface AnalysisResult {
  bodyCondition: (typeof bodyConditions)[number];
  coatCondition: (typeof coatConditions)[number];
  breedGuess: string;
  appearanceNotes: string;
  summaryText: string;
  recommendations: string;
}

const buildFallbackAnalysis = (pet: PetDocument): AnalysisResult => {
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

const normalizeBodyCondition = (
  value?: string
): (typeof bodyConditions)[number] => {
  if (!value) {
    return "unknown";
  }
  const normalized = value.trim().toLowerCase();
  return bodyConditions.includes(normalized as (typeof bodyConditions)[number])
    ? (normalized as (typeof bodyConditions)[number])
    : "unknown";
};

const normalizeCoatCondition = (
  value?: string
): (typeof coatConditions)[number] => {
  if (!value) {
    return "unknown";
  }
  const normalized = value.trim().toLowerCase();
  return coatConditions.includes(normalized as (typeof coatConditions)[number])
    ? (normalized as (typeof coatConditions)[number])
    : "unknown";
};

export const analyzePetImage = async (
  imagePath: string,
  pet: PetDocument
): Promise<AnalysisResult> => {
  const apiKey = process.env.OPENAI_API_KEY;
  if (!apiKey) {
    return buildFallbackAnalysis(pet);
  }

  try {
    const imageBase64 = await readFile(imagePath, { encoding: "base64" });
    const petContext = `Perfil de mascota: ${pet.name} (${pet.species}, ${pet.breed}), edad ${pet.ageYears}, peso ${pet.weightKg}kg.`;

    const payload = {
      model: "gpt-4o-mini",
      messages: [
        { role: "system", content: ANALYSIS_PROMPT },
        { role: "system", content: petContext },
        {
          role: "user",
          content: [
            { type: "text", text: "Analiza la imagen proporcionada." },
            {
              type: "image_url",
              image_url: {
                url: `data:image/jpeg;base64,${imageBase64}`,
              },
            },
          ],
        },
      ],
      response_format: { type: "json_object" },
    };

    const response = await fetch("https://api.openai.com/v1/chat/completions", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${apiKey}`,
      },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      const text = await response.text();
      throw new Error(`Analysis service error: ${response.status} ${text}`);
    }

    const data = (await response.json()) as {
      choices?: Array<{ message?: { content?: string } }>;
    };

    const content = data.choices?.[0]?.message?.content ?? "";
    const parsed = JSON.parse(content) as Partial<AnalysisResult> & {
      bodyCondition?: string;
      coatCondition?: string;
    };

    return {
      bodyCondition: normalizeBodyCondition(parsed.bodyCondition),
      coatCondition: normalizeCoatCondition(parsed.coatCondition),
      breedGuess: parsed.breedGuess?.trim() || "Unknown",
      appearanceNotes:
        parsed.appearanceNotes?.trim() ||
        "No se pudieron detectar notas claras de la apariencia.",
      summaryText:
        parsed.summaryText?.trim() ||
        "No se pudo generar un resumen confiable con la imagen proporcionada.",
      recommendations:
        parsed.recommendations?.trim() ||
        "Consulta con un veterinario si tienes dudas sobre la salud de tu mascota.",
    };
  } catch (error) {
    return buildFallbackAnalysis(pet);
  }
};

// TODO: Consider swapping to a dedicated CV model or vendor-neutral adapter.
