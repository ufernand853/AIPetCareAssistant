import mongoose, { Schema, type Document } from "mongoose";

export type PetSpecies = "dog" | "cat";
export type PetGender = "male" | "female" | "unknown";

export interface PetDocument extends Document {
  ownerId: mongoose.Types.ObjectId;
  name: string;
  species: PetSpecies;
  breed: string;
  ageYears: number;
  weightKg: number;
  gender: PetGender;
  notes?: string;
  photoUrl?: string;
  createdAt: Date;
  updatedAt: Date;
}

const PetSchema = new Schema<PetDocument>(
  {
    ownerId: { type: Schema.Types.ObjectId, ref: "User", required: true },
    name: { type: String, required: true },
    species: { type: String, enum: ["dog", "cat"], required: true },
    breed: { type: String, required: true },
    ageYears: { type: Number, required: true },
    weightKg: { type: Number, required: true },
    gender: { type: String, enum: ["male", "female", "unknown"], required: true },
    notes: { type: String },
    photoUrl: { type: String },
  },
  { timestamps: true }
);

export const Pet = mongoose.model<PetDocument>("Pet", PetSchema);
