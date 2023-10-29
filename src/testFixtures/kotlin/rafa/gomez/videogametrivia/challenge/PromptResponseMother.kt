package rafa.gomez.videogametrivia.challenge

object PromptResponseMother {


    fun answerDoesNotMatchChoice() =
        """
            Q: Who is the main character of Call of Duty: Modern Warfare?
            
            A. Alex Mason
            B. John Price
            C. Sam Fisher
            D. Marcus Fenix
            Answer: B. John Price
            Difficulty: Easy
            
            Q: What is the name of the antagonist in the game NieR: Automata?
            
            A. Joker
            B. Adam
            C. Jack
            D. Wilhelm
            Answer: D. Wilhelm
            Difficulty: Medium
            
            Topic: Game Lore and Storylines
            Q: In the game Haunting Ground, what is the name of Fiona's protector?
            
            A. Fanny
            B. Daniela
            C. Dante
            D. Hewie
            Answer: D. Hewie
            Difficulty: Easy
            
            Q: Who is the antagonist of the game Gears of War?
            
            A. Locust
            B. Juggernaut
            C. Big Daddy
            D. Boss
            Answer: A. Loc
            Difficulty: Easy
        """

    fun topicLessResponse() =
        """
            Q: Who is the main character of Call of Duty: Modern Warfare?
            
            A. Alex Mason
            B. John Price
            C. Sam Fisher
            D. Marcus Fenix
            Answer: B. John Price
            Difficulty: Easy
            
            Q: What is the name of the antagonist in the game NieR: Automata?
            
            A. Joker
            B. Adam
            C. Jack
            D. Wilhelm
            Answer: D. Wilhelm
            Difficulty: Medium
            
            Q: In the game Haunting Ground, what is the name of Fiona's protector?
            
            A. Fanny
            B. Daniela
            C. Dante
            D. Hewie
            Answer: D. Hewie
            Difficulty: Easy
            
            Q: Who is the antagonist of the game Gears of War?
            
            A. Locust
            B. Juggernaut
            C. Big Daddy
            D. Boss
            Answer: A. Locust
            Difficulty: Easy
        """.trimIndent()

    fun fullValidResponse() =
        """
            Topic: Game Lore and Storylines
            Q: Who is the main character of Call of Duty: Modern Warfare?
            
            A. Alex Mason
            B. John Price
            C. Sam Fisher
            D. Marcus Fenix
            Answer: B. John Price
            Difficulty: Easy
            
            Q: What is the name of the antagonist in the game NieR: Automata?
            
            A. Joker
            B. Adam
            C. Jack
            D. Wilhelm
            Answer: D. Wilhelm
            Difficulty: Medium
            
            Topic: Game Lore and Storylines
            Q: In the game Haunting Ground, what is the name of Fiona's protector?
            
            A. Fanny
            B. Daniela
            C. Dante
            D. Hewie
            Answer: D. Hewie
            Difficulty: Easy
            
            Q: Who is the antagonist of the game Gears of War?
            
            A. Locust
            B. Juggernaut
            C. Big Daddy
            D. Boss
            Answer: A. Locust
            Difficulty: Easy
        """.trimIndent()
}
