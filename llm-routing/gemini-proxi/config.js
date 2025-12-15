require('dotenv').config()
const { GoogleGenAI } = require('@google/genai');
const GEMINI_API_KEY = process.env.GEMINI_API_KEY

let api = null

module.exports = {
    'id': 'my-api',
    'port': 7050,
    'log': true,
    'routes': {
        '/api/v1/route': {
            'post': async (req, res, next) => {

                if (!api) {
                    api = new GoogleGenAI({ apiKey: GEMINI_API_KEY });
                }
                //validate req body to contain 'model: "gpt" | 'claude' and 'message': string
                if (!req.body.model || !req.body.message) {
                    return res.status(400).json({ error: 'Missing model or message' })
                }

                const result = await api.models.generateContent({
                    model: 'gemini-2.5-flash',
                    contents: req.body.message,
                });

                return res.json({
                    model: req.body.model,
                    response: result.text
                })
            }
        }
    }
};