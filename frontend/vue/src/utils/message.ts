import { ElMessage } from 'element-plus';

let messageInstance = null;

function showMessage(options) {
if (messageInstance) {
messageInstance.close(); // 关闭已有的消息实例
}

messageInstance = ElMessage({
...options,
onClose: () => {
messageInstance = null; // 消息关闭后重置实例
},
});
}

export default showMessage;