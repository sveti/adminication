# adminication

###for DB

##1.Add Parent1
{
    "user":{
    "username":"pesho",
    "password":"pesho",
    "email":"pesho@pesho.com",
    "role":"ROLE_PARENT",
    "name":"Parent1",
    "lastName":"LastName1"
	}
}
##2.Add Parent2
{
    "user":{
        "username":"maria",
        "password":"maria",
        "email":"maria@pesho.com",
        "role":"ROLE_PARENT",
        "name":"Parent2",
        "lastName":"LastName2"
    }
}
##3.Add Student1
{
    "user":{
        "username":"gosho",
        "password":"gosho",
        "email":"gosho@pesho.com",
        "role":"ROLE_STUDENT",
        "name":"Student1",
        "lastName":"LastName2"
    },
    "parent": {
        "id": 1,
        "user": {
            "id": 2,
            "username": "pesho",
            "email": "pesho@pesho.com",
            "password": "pesho",
            "role": "ROLE_PARENT",
            "name": "Parent1",
            "lastName": "LastName1"
        },
        "children": []
    }

}