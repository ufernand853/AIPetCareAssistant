import type { Request, Response, NextFunction } from "express";

export const errorHandler = (
  err: Error,
  _req: Request,
  res: Response,
  _next: NextFunction
): void => {
  const status = (err as Error & { status?: number }).status ?? 500;
  res.status(status).json({
    message: err.message || "Unexpected server error",
  });
};
