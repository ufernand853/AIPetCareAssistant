import type { PetDocument } from "../models/Pet";

const SYSTEM_DISCLAIMER =
  "You are an AI pet care assistant. You can give general advice on pet care, nutrition, grooming, and behavior. You are NOT a veterinarian and MUST always recommend seeing a vet for any serious concerns, symptoms, or diagnoses.";

export interface ChatResponse {
  content: string;
}

export const sendChatMessage = async (
  message: string,
  pet?: PetDocument | null
): Promise<ChatResponse> => {
  const apiKey = process.env.OPENAI_API_KEY;
  if (!apiKey) {
    return {
      content:
        "AI chat is not configured. Please set the OPENAI_API_KEY environment variable.",
    };
  }

  const petContext = pet
    ? `Pet profile: ${pet.name} (${pet.species}, ${pet.breed}), age ${pet.ageYears}, weight ${pet.weightKg}kg.`
    : "No pet profile provided.";

  const payload = {
    model: "gpt-4o-mini",
    messages: [
      { role: "system", content: SYSTEM_DISCLAIMER },
      { role: "system", content: petContext },
      { role: "user", content: message },
    ],
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
    throw new Error(`Chat service error: ${response.status} ${text}`);
  }

  const data = (await response.json()) as {
    choices?: Array<{ message?: { content?: string } }>;
  };

  const content = data.choices?.[0]?.message?.content ??
    "Sorry, I could not generate a response.";

  return { content };
};

// TODO: Swap to a pluggable LLM adapter to support other providers or local models.
