DoublyLinkedListNode* sortedInsert(DoublyLinkedListNode* llist, int data)

{
   DDoublyLinkedListNode* temp = llist;


   DoublyLinkedListNode* newNode = new Node();
   newNode->data = data;
   newNode->next = NULL;
   newNode->prev = NULL;


   if(temp == NULL)
   {
       temp = newNode;
       llist = temp;
       return llist;
   }

   while((newNode->data > temp->data) && temp->next != NULL)
   {
       temp = temp->next;
   }

   if(temp->next == NULL && newNode->data > temp->data )
   {
       newNode->prev = temp;
       temp->next = newNode;
       return llist;
   }

   newNode->prev = temp->prev;
   temp->prev = newNode;
   newNode->next = temp;


   if(newNode->prev != NULL)
   {
       newNode->prev->next = newNode;
   }

   if(newNode->prev == NULL)
   {
       temp = newNode;
       return temp;
   }

   return llist;

}
