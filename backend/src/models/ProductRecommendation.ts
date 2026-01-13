import mongoose, { Schema, type Document } from "mongoose";

export type ProductSpecies = "dog" | "cat";
export type TargetCondition =
  | "puppy_kitten"
  | "adult"
  | "senior"
  | "weight_control"
  | "sensitive_skin"
  | "sensitive_stomach";

export interface ProductRecommendationDocument extends Document {
  species: ProductSpecies;
  targetCondition: TargetCondition;
  title: string;
  brand: string;
  description: string;
  affiliateUrl: string;
  imageUrl: string;
}

const ProductRecommendationSchema = new Schema<ProductRecommendationDocument>(
  {
    species: { type: String, enum: ["dog", "cat"], required: true },
    targetCondition: {
      type: String,
      enum: [
        "puppy_kitten",
        "adult",
        "senior",
        "weight_control",
        "sensitive_skin",
        "sensitive_stomach",
      ],
      required: true,
    },
    title: { type: String, required: true },
    brand: { type: String, required: true },
    description: { type: String, required: true },
    affiliateUrl: { type: String, required: true },
    imageUrl: { type: String, required: true },
  },
  { timestamps: false }
);

export const ProductRecommendation = mongoose.model<ProductRecommendationDocument>(
  "ProductRecommendation",
  ProductRecommendationSchema
);
