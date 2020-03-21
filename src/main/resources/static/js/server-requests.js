const topicAddButton = document.querySelector('.add-topic button'); //class add-topic, element button
const topicAddInput = document.querySelector('.add-topic input');
const topicsList = document.querySelector('.topics-list ul');
const topicRemoveButtons = document.querySelectorAll(' .x'); //collection of buttons

const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
	if(xhr.readyState === 4 && xhr.status === 200){
		const res = xhr .responseText;
		topicsList.innerHTML = res;
		}
}

topicAddButton.addEventListener('click', function(){
	postTopics(topicAddInput.value);
	console.log(topicAddInput.value);
	topicAddInput.value = ""; //clear

})

//since it's going thru a collection --> needs a forEach loop
//below targets the list items (<li>) but we need to use the <ul> instead...see below this
// topicRemoveButtons.forEach(button =>{
// 	button.addEventListener('click', function(event){ //targets the hidden id?
// 									//moves us from the button to the anchor tag in our topics.html. Calling it again, moves us from the anchor tag to the input
// 		let topicId = event.target.previousElementSibling.previousElementSibling.value;
// 		//console.log(topicId);
// 		removeTopic(topicId);

// 	})
// })

//targets the <ul>
topicsList.addEventListener('click', function(event) {
	
	if(event.target.classList.contains('x')){
	let topicId = event.target.previousElementSibling.previousElementSibling.value;
 		console.log(topicId);
 		removeTopic(topicId);
	}
})



//posting a topic
function postTopics(topicName){
	xhr.open('POST', '/topics/' + topicName, true);
	xhr.send();
}

//removing a topic
function removeTopic(id){
	xhr.open('POST', '/topics/remove/' + id, true);
	xhr.send();
}
