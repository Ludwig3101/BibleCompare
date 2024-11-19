# BibleCompare

**BibleCompare** analyzes the linguistic differences between the **King James Bible** and the **Basic English Bible**. The Basic English Bible is a simplified version of the English language, utilizing a reduced vocabulary.

## Features
1. **Text Preprocessing**  
   Both texts were cleaned by:
   - Removing unnecessary characters.
   - Converting all text to lowercase.
   - Structuring the text into continuous flow for analysis.

2. **Analysis**  
   The program performs the following analyses:
   - **Word count** and **letter count**.
   - **Word length distribution**.
   - **Frequency analysis** of words and letters.
   - **Sentence length comparison**.
   - Detection of **archaic English pronouns** (e.g., "thee", "thou").

This project provides a detailed comparison of the two versions, highlighting how simplification impacts linguistic features.

## Analysis Summary

The analysis of the **King James Version** and the **Basic English Version** reveals significant differences between the two works. The King James Version employs a more complex and extensive vocabulary, which is evident in the higher number of unique words and longer word lengths. 

In contrast, the Basic English Version uses simpler and more accessible language, as reflected in the lower number of unique words and shorter word lengths. Additionally, the absence of archaic English pronouns highlights the modern and more comprehensible approach of the Basic English Version.

Despite these differences, there are also notable similarities, such as the comparable usage of letters in both texts. Interestingly, while the Basic English Version was expected to use simpler language, it actually contains more words than the King James Version. This demonstrates that accessibility is often achieved through more detailed and explicit phrasing, whereas the older version, despite or perhaps due to its complexity, often conveys meaning with fewer words.

## Development Environment

This project was developed using **IntelliJ IDEA Community Edition 2024.1**.  
The Java runtime environment used was **OpenJDK 22** (Oracle OpenJDK version 22.0.1).  

### System Specifications:
- **Operating System**: Windows 10
- **CPU**: 8-core processor
- **RAM**: 2048 MB

### Libraries and Tools:
1. **Java Standard Library**:  
   Used for core file management and data storage functionalities.

2. **LingoLibry**:  
   An additional library utilized for efficient text processing and analysis of the Bible texts.

Both libraries were essential for implementing the features and conducting the analysis efficiently.
