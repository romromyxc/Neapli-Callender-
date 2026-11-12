package com.example.nepalicalendar

data class NepaliFestival(val date: String, val name: String, val details: String)

object FestivalData {
    val festivals = listOf(
        NepaliFestival("2026-01-15", "Maghe Sankranti", "First day of Magh, winter solstice festival"),
        NepaliFestival("2026-01-23", "Saraswati Puja / Basanta Panchami", "Worship of goddess of knowledge"),
        NepaliFestival("2026-02-15", "Maha Shivaratri", "Great night of Lord Shiva, Pashupatinath festivity"),
        NepaliFestival("2026-02-18", "Gyalpo Lhosar", "Tibetan/Sherpa New Year celebration"),
        NepaliFestival("2026-03-03", "Fagu Purnima (Holi)", "Festival of Colors"),
        NepaliFestival("2026-03-19", "Ghode Jatra", "Horse racing festival celebrated in Kathmandu valley"),
        NepaliFestival("2026-04-14", "Nepali New Year 2083 BS", "Baisakh 1, national official new year"),
        NepaliFestival("2026-04-21", "Ram Navami", "Birth of Lord Rama"),
        NepaliFestival("2026-05-02", "Buddha Jayanti / Ubhauli", "Birth, enlightenment of Buddha and Kirat festival"),
        NepaliFestival("2026-08-28", "Janai Purnima / Raksha Bandhan", "Sacred thread changing and bond festival"),
        NepaliFestival("2026-08-29", "Gai Jatra", "Cow festival honoring departed family members"),
        NepaliFestival("2026-09-04", "Krishna Janmashtami", "Birth anniversary of Lord Krishna"),
        NepaliFestival("2026-09-14", "Haritalika Teej", "Fast and celebration dedicated to Parvati and Shiva"),
        NepaliFestival("2026-09-16", "Rishi Panchami", "Purification and homage to the Seven Sages"),
        NepaliFestival("2026-09-25", "Indra Jatra", "Rain god Indra festival, Kumari procession"),
        NepaliFestival("2026-10-11", "Ghatasthapana (Dashain Start)", "Beginning of 15-day Vijaya Dashami festival"),
        NepaliFestival("2026-10-18", "Maha Ashtami (Dashain)", "Sacrifices and worship of Goddess Kali"),
        NepaliFestival("2026-10-19", "Maha Navami (Dashain)", "Ayudha Puja and final preparations"),
        NepaliFestival("2026-10-20", "Vijaya Dashami (Bara Dashain)", "Elder blessings, Tika and Jamara"),
        NepaliFestival("2026-11-08", "Laxmi Puja (Tihar)", "Festival of lights, welcoming goddess of wealth"),
        NepaliFestival("2026-11-09", "Gobardhan Puja / Mha Puja", "Newari new year day and ox worship"),
        NepaliFestival("2026-11-10", "Bhai Tika (Tihar)", "Brothers and sisters celebration"),
        NepaliFestival("2026-11-15", "Chhath Puja", "Sun god worship on river banks"),
        NepaliFestival("2026-12-24", "Tamu Lhosar", "Gurung community new year celebration")
    )
}
