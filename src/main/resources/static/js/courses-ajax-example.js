const xhr = new XMLHttpRequest();
xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //console.log(xhr.responseText);
            const res = JSON.parse(xhr.response); //could use let instead of const here
            const container = document.querySelector('.container');
            console.log({
                res
            });

            res.forEach(function (course) {  //annonymouse function
                    const courseItem = document.createElement('div')
                    const name = document.createElement('h2')
                    name.innerText = course.name;

                    container.appendChild(courseItem)
                    courseItem.appendChild(name);
                });


            }
        }
        xhr.open('GET', 'http://localhost:8080/api/courses', true); //talks to my REST controller
        xhr.send();