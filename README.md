<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/Radch-enko/TechInterviewBot">
    <img src="images/project_logo.png" alt="Logo" width="50%" >
  </a>

<h3 align="center">Computer Science Interview Helper</h3>

  <p align="center">Telegram bot that simplifies technical interviews in IT</p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

This project aims to simplify technical interviews for developers. Currently, the idea is implemented in the form of a Telegram bot.

The bot has the following functionality:
- Parsing questions from an Excel file.
- Generating a list of questions based on selected topics and subtopics chosen by the interviewer.
- The bot sequentially presents the current interview question with a brief answer and the question's importance.
- The interviewer can record assessments of how the candidate answered. Quick buttons are available for "Excellent," "No experience," and "Superficial knowledge." The interviewer can also leave arbitrary text notes.
- At the end of the interview, the bot provides a simplified summary of the results.


<div align="center">
  <img src="image/../images/demo-app.gif" width="50%" />
</div>


<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Getting started

### Prerequisites

1. Create a Telegram Bot followwing instruction from [official guide](#https://core.telegram.org/bots#how-do-i-create-a-bot)
2. Source file with prepared questions [source.xls](telegramBot/src/main/resources/source.xlsx)

### Installation


2. Clone the repo
   ```sh
   git clone https://github.com/Radch-enko/TechInterviewBot.git
   ```
3. Enter your bot token in environment variables:
   ```
   TECH_INTERVIEW_BOT_TOKEN={YOUR_TOKEN}
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->

## Roadmap

- [ ] Deployment
- [ ] Training quiz for candidates
- [ ] English localization

See the [open issues](https://github.com/Radch-enko/TechInterviewBot.git) for a full list of proposed features (and
known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also
simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

Stanislav Radchenko - [@telegram](https://telegram.me/StanislavRadchenko) - stas.radchenko.den@gmail.com

Project Link: [https://github.com/Radch-enko/TechInterviewBot](https://github.com/Radch-enko/TechInterviewBot)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [kotlin-telegram-bot](https://github.com/kotlin-telegram-bot/kotlin-telegram-bot) - 🤖 A wrapper for the Telegram Bot API written in Kotlin

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Radch-enko/TechInterviewBot.svg?style=for-the-badge
[contributors-url]: https://github.com/Radch-enko/TechInterviewBot/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Radch-enko/TechInterviewBot.svg?style=for-the-badge
[forks-url]: https://github.com/Radch-enko/TechInterviewBot/network/members
[stars-shield]: https://img.shields.io/github/stars/Radch-enko/TechInterviewBot.svg?style=for-the-badge
[stars-url]: https://github.com/Radch-enko/TechInterviewBot/stargazers
[issues-shield]: https://img.shields.io/github/issues/Radch-enko/TechInterviewBot.svg?style=for-the-badge
[issues-url]: https://github.com/Radch-enko/TechInterviewBot/issues
[license-shield]: https://img.shields.io/github/license/Radch-enko/TechInterviewBot.svg?style=for-the-badge
[license-url]: https://github.com/Radch-enko/TechInterviewBot/blob/main/LICENSE
