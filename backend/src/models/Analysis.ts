import mongoose, { Schema, type Document } from "mongoose";

export type BodyCondition = "underweight" | "ideal" | "overweight" | "unknown";
export type CoatCondition = "normal" | "dry" | "shedding" | "unknown";

export interface AnalysisDocument extends Document {
  petId: mongoose.Types.ObjectId;
  ownerId: mongoose.Types.ObjectId;
  imageUrl: string;
  bodyCondition: BodyCondition;
  coatCondition: CoatCondition;
  summaryText: string;
  recommendations: string;
  createdAt: Date;
}

const AnalysisSchema = new Schema<AnalysisDocument>(
  {
    petId: { type: Schema.Types.ObjectId, ref: "Pet", required: true },
    ownerId: { type: Schema.Types.ObjectId, ref: "User", required: true },
    imageUrl: { type: String, required: true },
    bodyCondition: {
      type: String,
      enum: ["underweight", "ideal", "overweight", "unknown"],
      default: "unknown",
    },
    coatCondition: {
      type: String,
      enum: ["normal", "dry", "shedding", "unknown"],
      default: "unknown",
    },
    summaryText: { type: String, required: true },
    recommendations: { type: String, required: true },
  },
  { timestamps: { createdAt: true, updatedAt: false } }
);

export const Analysis = mongoose.model<AnalysisDocument>("Analysis", AnalysisSchema);
