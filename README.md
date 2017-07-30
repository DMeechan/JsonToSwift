# JsonToSwift
Simple Java application which takes a JSON file and converts it to Swift code

Since I couldn't get JSON deserialisation to work in Swift for our arrays of objects, I ended up switching to CoreData and needed some way to import the contents of the JSON file into the CoreData database.
Since I still feel more comfortable in Java than Swift (and had already successfully gotten JSON serialisation and deserialisation to work in Java months ago (thanks to the GSN GSON library)), I made this short program to take a JSON file containing an array of Questions and convert it to Swift code.

Probably not much use outside of the 100 riddles app, but could be handy if you need an example of JSON serialisation and deserialisation in Java.
