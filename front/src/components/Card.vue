<template>
<v-hover>
  <template v-slot="{ hover }">
  <v-card
    :elevation="hover ? 6 : 0"
    class="mx-auto"
    max-width="400"
    height="150px"
    outlined
  >
    <v-list-item two-line>
      <v-btn-fab
        color="primary"
        elevation="2"
        fab
        small
      ></v-btn-fab>
      <v-list-item-content>
        <div class="headline mb-1 d-inline-block text-truncate">{{parent.number}}. {{parent.title}}</div>
        <v-list-item-title class="overline mb-4">{{parent.classitication}}</v-list-item-title>
        <v-list-item-subtitle class="font-weight-black">푼사람수 {{parent.solved}} 명</v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
        tile
        size="100"
        align="bottom"
      ><h2 class="yellow--text text-darken-1">{{rank()}}</h2></v-list-item-avatar>
    </v-list-item>
  </v-card>
  </template>
</v-hover>
</template>

<script>
export default {
  name: "Card",
  props : ['parent'],
  data: () => ({
    //problems : this.parent,
  }),
  methods: {
    rank: function() {
      let rank = '';
      if(this.parent.level.includes('Bronze'))
        rank = '☆☆';
      else if(this.parent.level.includes('Silver'))
        rank = '★☆';
      else if(this.parent.level.includes('Gold'))
        rank = '★★';
      else if(this.parent.level.includes('Platinum'))
        rank = '★★☆';
      else if(this.parent.level.includes('Diamond'))
        rank = '★★★';
      else if(this.parent.level.includes('Ruby'))
        rank = '★★★★';
      
      return rank;
    } 
  }
}
</script>