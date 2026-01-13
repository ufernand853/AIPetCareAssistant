# AI Pet Care Assistant Backend

## Overview
Node.js + Express API powering the AI Pet Care Assistant mobile app. Provides authentication, pet management, photo analysis, reminders, and AI chat endpoints.

## Requirements
- Node.js 18+
- MongoDB instance

## Setup
1. Install dependencies:
   ```bash
   npm install
   ```
2. Create a `.env` file based on `.env.example`.
3. Start the dev server:
   ```bash
   npm run dev
   ```

## Scripts
- `npm run dev` - run with nodemon + ts-node
- `npm run build` - compile TypeScript to `dist`
- `npm run start` - run compiled server

## Environment Variables
- `PORT` - server port (default 4000)
- `MONGODB_URI` - Mongo connection string
- `JWT_SECRET` - JWT signing secret
- `OPENAI_API_KEY` - API key for OpenAI chat completions

## API Endpoints
Base URL: `/api`

### Auth
- `POST /auth/register`
- `POST /auth/login`
- `GET /auth/me`

### Pets
- `GET /pets`
- `POST /pets`
- `GET /pets/:id`
- `PUT /pets/:id`
- `DELETE /pets/:id`
- `POST /pets/:id/photo` (multipart field `image`)
- `GET /pets/:id/analyses`
- `GET /pets/analyses/:analysisId`

### Reminders
- `GET /pets/:id/reminders`
- `POST /pets/:id/reminders`
- `PUT /reminders/:id`
- `DELETE /reminders/:id`

### Recommendations
- `GET /recommendations?species=dog&targetCondition=adult`

### Chat
- `POST /chat` body `{ petId?, message }`

## Notes
- The analysis service currently uses a stubbed heuristic and should be replaced with a real CV model in production.
- AI responses always include a safety disclaimer and should not be considered medical advice.

## Future Extensions
- Plug in a real CV pipeline or third-party vision API.
- Add FCM push notifications for reminders.
- Extend multi-language support in client and server prompts.
