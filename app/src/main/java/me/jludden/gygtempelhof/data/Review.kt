package me.jludden.gygtempelhof.data

data class Review(
        val id: Int,
        val rating: Float,
        val title: String,
        val message: String,
        val author: String,
        val foreignLanguage: Boolean,
        val date: String,
        val languageCode: String,
        val traveler_type: String,
        val reviewerName: String,
        val reviewerCountry: String
)

//important ones: review author, title, message, date, rating, language code


//todo date format

//todo date_unformatted ?? no examples with data here

//todo enum types for language, traveler_type, ... etc?
//"languageCode":"de",
//"traveler_type":"family_old",


/*
"review_id":2494133,
"rating":"5.0",
"title":"Absolut empfehlenswert",
"message":"Unser Guide Lars war spitze und hat uns mit vielen Anekdoten und historischen Hintergründen spannend unterhalten. Eine Geschichtsstunde vom alllerfeinsten!",
"author":"Brigitte – Germany",
"foreignLanguage":true,
"date":"May 7, 2018",
"date_unformatted":{
},
"languageCode":"de",
"traveler_type":"family_old",
"reviewerName":"Brigitte",
"reviewerCountry":"Germany"
 */