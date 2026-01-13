import mongoose, { Schema, type Document } from "mongoose";

export type ReminderType =
  | "vaccine"
  | "antiparasitic"
  | "grooming"
  | "vet_visit"
  | "custom";

export interface ReminderDocument extends Document {
  petId: mongoose.Types.ObjectId;
  ownerId: mongoose.Types.ObjectId;
  type: ReminderType;
  title: string;
  description?: string;
  dueDate: Date;
  isCompleted: boolean;
  createdAt: Date;
  updatedAt: Date;
}

const ReminderSchema = new Schema<ReminderDocument>(
  {
    petId: { type: Schema.Types.ObjectId, ref: "Pet", required: true },
    ownerId: { type: Schema.Types.ObjectId, ref: "User", required: true },
    type: {
      type: String,
      enum: ["vaccine", "antiparasitic", "grooming", "vet_visit", "custom"],
      required: true,
    },
    title: { type: String, required: true },
    description: { type: String },
    dueDate: { type: Date, required: true },
    isCompleted: { type: Boolean, default: false },
  },
  { timestamps: true }
);

export const Reminder = mongoose.model<ReminderDocument>("Reminder", ReminderSchema);
