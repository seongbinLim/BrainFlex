<template>
    <v-container align="center">
      <v-autocomplete
        v-model="value"
        :items="items"
        dense
        filled
        label="알고리즘"
      ></v-autocomplete>
    </v-container>
</template>

<script>
  export default {
    name: "Ffilter",
    data: () => ({
      items: ['DP', 'BFS', 'DFS', '완전탐색', '트리', '백트래킹'],
      value: null,
    }),
    watch: {
      value() {
        this.$emit('updateFilter', this.value);
      }
    }


}
</script>