[![Build Status](https://travis-ci.org/nicolasmanic/JRockets.svg?branch=master)](https://travis-ci.org/nicolasmanic/JRockets)
[![codecov](https://codecov.io/gh/nicolasmanic/JRockets/branch/master/graph/badge.svg)](https://codecov.io/gh/nicolasmanic/JRockets)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0843fb7e829a4c3e901a10363f6b1c7a)](https://www.codacy.com/app/nicolasmanic/JRockets?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nicolasmanic/JRockets&amp;utm_campaign=Badge_Grade)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/nicolasmanic/JRockets/blob/master/LICENCE)
# JRockets

A wrapper for Reddit that turns the static Rest API into a close to realtime API using Websockets & Streams.
 
Originally inspired and based on [Rockets](https://github.com/rockets/rockets).

## Technology Stack

- Spring Boot
- RabbitMQ
- Websockets/STOMP

## Topology

![topology](https://user-images.githubusercontent.com/4174162/30293673-0801c608-9743-11e7-990a-4d127d2b75cb.gif)


## API

If you want use the provided clients to view the stream of comments and posts use the following endpoints:

- `http://localhost:8080/comments`
- `http://localhost:8080/posts`

The sockets endpoint are available at:
- `http://localhost:8080/commentStream`
- `http://localhost:8080/postStream`

The stomp client can subscribe to:
- `http://localhost:8080/topic/comments`
- `http://localhost:8080/topic/posts`

## How to run

Before you start you will need to have a RabbitMQ instance running on port 5672 (default). To run JRockets
just execute: `mvn spring-boot:run`.

**Todo**:
- [ ] Build docker images

## Feedback

For any feedback please contact me at [linkedin](https://www.linkedin.com/in/nick-kanakis-24b34677)
