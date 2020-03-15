const topicAddButton = document.querySelector('.add-topic button'); //class add-topic, element button
const topicAddInput = document.querySelector('.add-topic input');
const topicsList = document.querySelector('.topics-list ul');

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
	
function postTopics(topicName){
	xhr.open('POST', 'topics/' + topicName, true);
	xhr.send();
}