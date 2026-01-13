import type { Request, Response } from "express";
import bcrypt from "bcryptjs";
import { User } from "../models/User";
import { signToken } from "../utils/jwt";
import type { AuthenticatedRequest } from "../middlewares/authMiddleware";

export const register = async (req: Request, res: Response): Promise<void> => {
  const { email, password } = req.body as { email?: string; password?: string };
  if (!email || !password) {
    res.status(400).json({ message: "Email and password are required" });
    return;
  }

  const existing = await User.findOne({ email });
  if (existing) {
    res.status(409).json({ message: "Email already registered" });
    return;
  }

  const passwordHash = await bcrypt.hash(password, 10);
  const user = await User.create({ email, passwordHash });
  const token = signToken(user.id);

  res.status(201).json({
    token,
    user: { id: user.id, email: user.email },
  });
};

export const login = async (req: Request, res: Response): Promise<void> => {
  const { email, password } = req.body as { email?: string; password?: string };
  if (!email || !password) {
    res.status(400).json({ message: "Email and password are required" });
    return;
  }

  const user = await User.findOne({ email });
  if (!user) {
    res.status(401).json({ message: "Invalid credentials" });
    return;
  }

  const isValid = await bcrypt.compare(password, user.passwordHash);
  if (!isValid) {
    res.status(401).json({ message: "Invalid credentials" });
    return;
  }

  const token = signToken(user.id);
  res.json({ token, user: { id: user.id, email: user.email } });
};

export const me = async (req: AuthenticatedRequest, res: Response): Promise<void> => {
  const user = await User.findById(req.userId).select("email");
  if (!user) {
    res.status(404).json({ message: "User not found" });
    return;
  }

  res.json({ id: user.id, email: user.email });
};
