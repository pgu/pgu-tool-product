
// pictures
function showDescription() {
	sendGoToPortal(window.product_ref, 'description:');
};

// description
function showPictures() {
	sendGoToPortal(window.product_ref, 'pictures:');
};

// common
function displayOtherProduct(product_ref) {
	goToOtherItemInPortal('description:' + product_ref);	
};

function sendToPortal(msg) {
	
    var fmt_msg = JSON.stringify(msg);
    console.log('[tool] ' + fmt_msg);

    console.log('[tool] ' + window.recipient); // TBD in jsp files
    
    parent.postMessage(fmt_msg, window.recipient);
    console.log('[tool] send to portal [done]');
};

function replaceMenuNewForMenuProduct(product_id) {
        console.log('[tool] send menu to portal [' + product_id + ']: ' + window.frame_id);

        var msg = {};
        msg.type = 'replace_menu';
        msg.frame_id_to_remove = window.frame_id;
        msg.menu_code_to_show = 'description:' + product_id;
        
        sendToPortal(msg);
};

function sendTitleToPortal(title) {
	var sendTitle = function() {
		console.log('[tool] send [title] to portal ' + title);
		
		var msg = {};
		msg.type = 'menu_title';
		msg.frame_id = window.frame_id;
		msg.title = title;
		
		sendToPortal(msg);
	};
	
	setTimeout(function(){sendTitle();}, 400);
};

function sendGoToPortal(product_id, menu_tag) {
	console.log('[tool] send [go] to portal ' + menu_tag);
	
	var msg = {};
	msg.type = 'go_to';
	msg.frame_id = window.frame_id;
	msg.menu_code = menu_tag + product_id;

	sendToPortal(msg);
};

function goToOtherItemInPortal(menu_code) {
	console.log('[tool] [go to other] to portal ' + menu_code);
	
	var msg = {};
	msg.type = 'go_to_other_item';
	msg.frame_id = window.frame_id;
	msg.menu_code = menu_code;

	sendToPortal(msg);
};

function get_listener_to_portal() {
    return function receiver(e) {

        console.log('[tool] receive message from portal');
        var msg = JSON.parse(e.data);
        
        console.log(msg);
        if (msg.type === 'something') {
            // do something...

        } else {
            console.log('[tool] Unsupported type ' + msg.type);
        }

     };
};

function is_in_portal() {
	 return window !== window.parent;
};

function page_init() {
	// hook
	console.log('page_init');
};

$(window).load(function () {
	console.log('>> onload');
	
	var url = window.location.href;
	
    if (url.indexOf("frameId=") > -1) {
        var queue = url.split("frameId=")[1];

        if (queue.indexOf("&") > -1) {
            window.frame_id = queue.split("&")[0];

        } else {
        	window.frame_id = queue;
        }
    }
    
//    window.frame_id = "description:99999";
    
    console.log("frame id: " + window.frame_id);

	addEventListener('message', get_listener_to_portal, false);
	
	page_init();
});
