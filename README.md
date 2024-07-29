# Getting Started

### Reference Documentation
This project is a simple GitHub analyzer which give to user information about all public repos of given owner which are not fork.

### Endpoint

GET /repo/{username}

Example:
GET /repo/owner

Response:

    [
        {
            "name": "repo1",
            "owner": "owner",
            "branches": [
                {
                    "name": "master",
                    "sha": "b33a9c4ed5f4re54e54ea0e"
                }
            ]
        },
        {
            "name": "repo2",
            "owner": "owner",
            "branches": [
                {
                    "name": "master",
                    "sha": "b33adfgfege7ghr8ht8e12fa0e"
                },
                {
                    "name": "dev",
                    "sha": "b33a9sfg5fd4g56dg4ergererge"
                }
            ]
        }
    ]
